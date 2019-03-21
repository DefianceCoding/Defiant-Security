package me.defiancecoding.defiantsecurity.api.validation;

import me.defiancecoding.defiantsecurity.exceptions.InvalidIPException;

public class IPValidation {

	private String ip;

	public IPValidation(String ip) {
		this.ip = ip;
	}

	public boolean check() throws InvalidIPException {

		if (ip.equalsIgnoreCase("127.0.0.1") || ip.equalsIgnoreCase("192.168.1.1")
				|| ip.equalsIgnoreCase("192.168.0.1")) {
			return false;
		}

		else if (ip == null || ip.length() < 7 || ip.length() > 15)
			return false;

		for (int i = 0; i < ip.length(); i++) {
			if (!Character.isDigit(ip.charAt(i)) && ip.charAt(i) != '.')
				return false;
		}

		try {
			int x = 0;
			int y = ip.indexOf('.');

			if (y == -1 || ip.charAt(x) == '-' || Integer.parseInt(ip.substring(x, y)) > 255)
				return false;

			x = ip.indexOf('.', ++y);
			if (x == -1 || ip.charAt(y) == '-' || Integer.parseInt(ip.substring(y, x)) > 255)
				return false;

			y = ip.indexOf('.', ++x);
			return !(y == -1 || ip.charAt(x) == '-' || Integer.parseInt(ip.substring(x, y)) > 255
					|| ip.charAt(++y) == '-' || Integer.parseInt(ip.substring(y)) > 255
					|| ip.charAt(ip.length() - 1) == '.');

		} catch (NumberFormatException e) {
			return false;
		}
	}

}
