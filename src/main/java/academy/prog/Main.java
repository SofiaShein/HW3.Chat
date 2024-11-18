package academy.prog;

import java.io.IOException;
import java.util.Scanner;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Set;


public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();
			System.out.println("Enter your password: ");
			String password = scanner.nextLine();

			int responseCode = authorizeUser(login, password);

			if (responseCode == 200) {
				System.out.println("Logged in successfully!");

				Set<String> onlineUsers = Utils.getOnlineUsers();
				System.out.println("Online users: " + onlineUsers);

				Thread th = new Thread(new GetThread(login));
				th.setDaemon(true);
				th.start();

				while (true) {
					System.out.println("Enter recipient (or press Enter for public message): ");
					String recipient = scanner.nextLine();
					if (recipient.isEmpty()) recipient = null;

					System.out.println("Enter your message: ");
					String text = scanner.nextLine();
					if (text.isEmpty()) break;

					Message m = new Message(login, recipient, text);
					int res = m.send(Utils.getURL() + "/add");

					if (res != 200) {
						System.out.println("HTTP error occurred: " + res);
						return;
					}
				}
			} else {
				System.out.println("Invalid credentials! Please try again.");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	private static int authorizeUser(String login, String password) throws IOException {
		String data = "login=" + login + "&password=" + password;

		URL url = new URL(Utils.getURL() + "/login");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		try (OutputStream os = conn.getOutputStream()) {
			os.write(data.getBytes(StandardCharsets.UTF_8));
		}

		return conn.getResponseCode();
	}
}
