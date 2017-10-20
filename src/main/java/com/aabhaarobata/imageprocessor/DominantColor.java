package com.aabhaarobata.imageprocessor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DominantColor {
	private static final Logger log = LoggerFactory.getLogger(DominantColor.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getDominantColor(BufferedImage image, int x, int y, int w, int h) {
		Map<String, Integer> colormap = new HashMap();
		int width = image.getWidth();
		int height = image.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);
		int x2 = x + w;
		int y2 = y + h;
		log.debug("x+w::" + x2);
		log.debug("y+h::" + y2);

		String dominantColor = "";
		for (; y < y2; y++) {
			for (; x < x2; x++) {
				int p = image.getRGB(x, y);

				updateMAP(colormap, getColorofPixel(p));
			}
		}
		if (!colormap.isEmpty()) {
			Map<String, Integer> result = colormap.entrySet().stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
							LinkedHashMap::new));
			log.debug("Colors::{}", result);
			dominantColor = result.keySet().iterator().next();

		}
		return dominantColor;
	}

	private void updateMAP(Map<String, Integer> colorMap, String color) {
		if (colorMap.get(color) != null) {
			int count = colorMap.get(color) + 1;
			colorMap.put(color, count);
		} else {
			colorMap.put(color, 1);
		}

	}

	public String getColorofPixel(int p) {
		String color = "";
		int a = (p >> 24) & 0xff;
		double r = (p >> 16) & 0xff;
		double g = (p >> 8) & 0xff;
		double b = p & 0xff;

		float hsb[] = new float[3];
		Color.RGBtoHSB((int) r, (int) g, (int) b, hsb);

		if (hsb[1] < 0.1 && hsb[2] > 0.9) {
			// nearlyWhite
			color = "WHITE";
		} else if (hsb[2] < 0.1) {
			// nearlyBlack
			color = "BLACK";
		} else {
			float deg = hsb[0] * 360;

			if ((deg >= 0 && deg < 31) || (deg > 330)) {
				color = "RED";

			} else if (deg >= 31 && deg < 71) {
				color = "YELLOW";
			} else if (deg >= 71 && deg < 176) {
				color = "GREEN";
			} else if (deg >= 176 && deg < 271) {
				color = "BLUE";
			} else if (deg >= 271 && deg < 331) {
				color = "MAGENTA";
			}

		}
		return color;
	}
}
