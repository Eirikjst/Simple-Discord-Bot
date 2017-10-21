package com.eirikjst;

import java.util.Scanner;

import sx.blah.discord.util.DiscordException;

public class Main {
	public static void main(String[] args) {
		BotUtility bt = null;
		Scanner sc = new Scanner(System.in);
		boolean runbot = true;
		boolean botConnected = false;
		while (runbot) {
			System.out.print("1: login\n2: logout\n");
			int option = 0;
			try {
				option = Integer.parseInt(sc.next());
			} catch (NumberFormatException e) {
				System.out.println("Not a valid operation\n");
			}
			switch (option) {
				case 1: if (botConnected) {
							System.out.println("Bot already connected");
							break;
						}
						System.out.print("\nProvide bot token\n");
						try {
							bt = new BotUtility(sc.next());
						} catch (DiscordException e) {
							e.printStackTrace();
						}
						if (bt != null) {
							if (bt.login() == true) botConnected = true;
							break;
						} else {
							System.out.println("kek");
						}
						break;
						
				case 2: sc.close();
						bt.logout();
						runbot = false;
						break;
						
				default: System.out.println("No valid operation");
						 break;
			}
		}
	}
}
