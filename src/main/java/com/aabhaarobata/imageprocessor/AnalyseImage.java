package com.aabhaarobata.imageprocessor;

//use eyedropper to select the color 
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aabhaarobata.web.forms.ImageProcessForm;

//sharpen
//extract dominant color and the color pallette 
//select color 
//smartblur 
public class AnalyseImage {
	private static final Logger log = LoggerFactory.getLogger(AnalyseImage.class);

	public static void main(String[] args) throws IOException {
		LocalTime startTime = LocalTime.now();
		BufferedImage image = null;

		BufferedImage processedimage = null;
		// String imagePath =
		// "D:\\sumit\\projects\\misc\\misc\\src\\main\\java\\testing\\ball.jpg";

		String imagePath = "D:\\sumit\\projects\\misc\\misc\\src\\main\\java\\testing\\DSC_0141.JPG";

		AnalyseImage engine = new AnalyseImage();
		image = engine.readImage(imagePath);

		// log.debug("DominantColor::{}", new
		// DominantColor().getDominantColor(image, 43, 390, 114, 113));

		/*
		 * processedimage = engine.processImagetoGreyScale(image);
		 * log.debug("Saved the image::{}", engine.writeImage(imagePath +
		 * ".grey.png", processedimage, "png"));
		 * 
		 * processedimage = engine.processImagetoSepia(image);
		 * log.debug("Saved the image::{}", engine.writeImage(imagePath +
		 * ".sepia.png", processedimage, "png"));
		 * 
		 * processedimage = engine.processImagetonegative(image);
		 * log.debug("Saved the image::{}", engine.writeImage(imagePath +
		 * ".negative.png", processedimage, "png"));
		 * 
		 * processedimage = engine.processImagemirror(image);
		 * log.debug("Saved the image::{}", engine.writeImage(imagePath +
		 * ".mirror.png", processedimage, "png"));
		 * 
		 * processedimage = engine.processImagetint(image, 0.25);
		 * log.debug("Saved the image:: {}", engine.writeImage(imagePath +
		 * ".tint.png", processedimage, "png"));
		 * 
		 * processedimage = engine.processImageshade(image, 0.25);
		 * log.debug("Saved the image:: {}", engine.writeImage(imagePath +
		 * ".shade.png", processedimage, "png"));
		 */

		// processedimage = engine.processImage(image, 0.25);
		// log.debug("Saved the image:: {}", engine.writeImage(imagePath +
		// ".processed.png", processedimage, "png"));

		// processedimage = engine.processImageskin(image, 0.25);
		// log.debug("Saved the image:: {}", engine.writeImage(imagePath +
		// ".processedskin.png", processedimage, "png"));

		// gaussian blur
		// https://stackoverflow.com/questions/39684820/java-implementation-of-gaussian-blur
		// *****use this int[] filter = {1, 2, 1, 2, 2, 2, 1, 2, 1};
		// int[] filter = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		// int[] filter = {1, 2, 1, 2, 0, 2, 1, 2, 1};
		// int[] filter = {0, -1, 0, -1, 5, -1, 0, -1, 0};
		/*
		 * int filterWidth = 3; int i =0 ; do{ image = engine.blur(image,
		 * filter, filterWidth); i++ ; } while(i<20); processedimage =
		 * engine.blur(image, filter, filterWidth);
		 * log.debug("Saved the image:: {}", engine.writeImage(imagePath +
		 * ".blurred.png", processedimage, "png"));
		 */

		// SmartBlurFilter filter = new SmartBlurFilter();
		// processedimage = filter.filter(image, null);
		// log.debug("Saved the image:: {}", engine.writeImage(imagePath +
		// ".smartblurred.png", processedimage, "png"));

		// UnsharpFilter filter = new UnsharpFilter();
		// processedimage = filter.filter(image, null);
		// log.debug("Saved the image:: {}", engine.writeImage(imagePath +
		// ".sharpened.png", processedimage, "png"));

		/*
		 * CMap result = ColorThief.getColorMap(image, 5); VBox dominantColor =
		 * result.vboxes.get(0);
		 * 
		 * ColorThiefTest test = new ColorThiefTest();
		 * 
		 * test.test(imagePath);
		 * 
		 * test.saveToHTMLFile(imagePath+".html");
		 */

		ImageProcessForm imageProcessForm = new ImageProcessForm();
		imageProcessForm.setX1("185");
		imageProcessForm.setY1("595");
		imageProcessForm.setW("21");
		imageProcessForm.setH("15");
		
		imageProcessForm.setX11("374");
		imageProcessForm.setY11("724");
		imageProcessForm.setW1("23");
		imageProcessForm.setH1("21");
				
		
		processedimage = engine.processSelectiveColor(image, imageProcessForm);
		log.debug("Saved the image:: {}", engine.writeImage(imagePath + ".processedselectedcolor.png", processedimage, "png"));
		LocalTime endTime = LocalTime.now();
		log.debug("Ended - duration={}",
				DurationFormatUtils.formatDuration(Duration.between(startTime, endTime).toMillis(), "HH:mm:ss:SSS"));

	}

	public BufferedImage readImage(String imagePath) throws IOException {
		File f = null;
		BufferedImage image = null;
		f = new File(imagePath);
		image = ImageIO.read(f);

		return image;
	}

	public boolean writeImage(String imagePath, BufferedImage image, String format) throws IOException {
		File f = null;
		format = format == null ? "jpg" : format;
		f = new File(imagePath);
		ImageIO.write(image, format, f);
		return true;
	}

	private BufferedImage processImagetoGreyScale(BufferedImage img) {

		int width = img.getWidth();
		int height = img.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);
		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				// calculate average
				int avg = (r + g + b) / 3;

				// replace RGB value with avg to convert to greyscale
				p = (a << 24) | (avg << 16) | (avg << 8) | avg;

				mimg.setRGB(x, y, p);
			}
		}

		return mimg;
	}

	private BufferedImage processImagetoSepia(BufferedImage img) {

		int width = img.getWidth();
		int height = img.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);
		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				// calculate tr, tg, tb
				int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
				int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
				int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

				r = tr > 255 ? 255 : tr;
				g = tr > 255 ? 255 : tg;
				b = tr > 255 ? 255 : tb;

				// set new RGB value
				p = (a << 24) | (r << 16) | (g << 8) | b;

				mimg.setRGB(x, y, p);
			}
		}

		return mimg;
	}

	private BufferedImage processImagetonegative(BufferedImage img) {

		int width = img.getWidth();
		int height = img.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);
		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				// subtract RGB from 255
				r = 255 - r;
				g = 255 - g;
				b = 255 - b;

				// set new RGB value
				p = (a << 24) | (r << 16) | (g << 8) | b;

				mimg.setRGB(x, y, p);
			}
		}

		return mimg;
	}

	private BufferedImage processImagemirror(BufferedImage img) {

		int width = img.getWidth();
		int height = img.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);

		// BufferedImage for mirror image
		BufferedImage mimg = new BufferedImage(width * 2, height, BufferedImage.TYPE_INT_ARGB);
		// create mirror image pixel by pixel

		for (int y = 0; y < height; y++) {
			for (int lx = 0, rx = width * 2 - 1; lx < width; lx++, rx--) {
				int p = img.getRGB(lx, y);

				// set mirror image pixel value - both left and right
				mimg.setRGB(lx, y, p);
				mimg.setRGB(rx, y, p);
			}
		}

		return mimg;
	}

	// A tint is produced by "ligthening" a hue or "adding white"
	private BufferedImage processImagetint(BufferedImage img, double tint_factor) {

		int width = img.getWidth();
		int height = img.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);
		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				double r = (p >> 16) & 0xff;
				double g = (p >> 8) & 0xff;
				double b = p & 0xff;

				// subtract RGB from 255
				r = r + (255 - r) * tint_factor;
				g = g + (255 - g) * tint_factor;
				b = b + (255 - b) * tint_factor;

				// set new RGB value
				p = (a << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;

				mimg.setRGB(x, y, p);
			}
		}

		return mimg;
	}

	// A shade is produced by "darkening" a hue or "adding black"

	private BufferedImage processImageshade(BufferedImage img, double shade_factor) {

		int width = img.getWidth();
		int height = img.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);
		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				double r = (p >> 16) & 0xff;
				double g = (p >> 8) & 0xff;
				double b = p & 0xff;

				// subtract RGB from 255
				r = r * (1 - shade_factor);
				g = g * (1 - shade_factor);
				b = b * (1 - shade_factor);

				// set new RGB value
				p = (a << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;

				mimg.setRGB(x, y, p);
			}
		}

		return mimg;
	}
	// Y = 0.2126*R + 0.7152*G + 0.0722*B
	// Then check if the value is nearer to 0 or to 255 and choose black or
	// white accordingly
	// color c = Y < 128 ? black : white

	private BufferedImage processImage(BufferedImage img, double tint_factor) {

		int width = img.getWidth();
		int height = img.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);
		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				double r = (p >> 16) & 0xff;
				double g = (p >> 8) & 0xff;
				double b = p & 0xff;

				float hsb[] = new float[3];
				Color.RGBtoHSB((int) r, (int) g, (int) b, hsb);

				if (hsb[1] < 0.1 && hsb[2] > 0.9) {
					// nearlyWhite
				} else if (hsb[2] < 0.1) {
					// nearlyBlack
				} else {
					float deg = hsb[0] * 360;

					if (deg >= 0 && deg < 30) {
						// red
						r = r + (255 - r) * tint_factor;
						g = g + (255 - g) * tint_factor;
						b = b + (255 - b) * tint_factor;
					} else if (deg >= 30 && deg < 90) {
						// yellow
						r = r + (255 - r) * tint_factor;
						g = g + (255 - g) * tint_factor;
						b = b + (255 - b) * tint_factor;
					} else if (deg >= 90 && deg < 150) {
						// green
						r = r + (255 - r) * tint_factor;
						g = g + (255 - g) * tint_factor;
						b = b + (255 - b) * tint_factor;
					} else if (deg >= 150 && deg < 210) {
						// cyan
						r = r + (255 - r) * tint_factor;
						g = g + (255 - g) * tint_factor;
						b = b + (255 - b) * tint_factor;
					} else if (deg >= 210 && deg < 270) {
						// blue
						r = r + (255 - r) * tint_factor;
						g = g + (255 - g) * tint_factor;
						b = b + (255 - b) * tint_factor;
					} else if (deg >= 270 && deg < 330) {
						// magenta
						r = r + (255 - r) * tint_factor;
						g = g + (255 - g) * tint_factor;
						b = b + (255 - b) * tint_factor;
					} else {
						// red
						r = r + (255 - r) * tint_factor;
						g = g + (255 - g) * tint_factor;
						b = b + (255 - b) * tint_factor;
					}

					/*
					 * if (deg >= 0 && deg < 30) { // red r = r + (255 - r) *
					 * tint_factor; g = g + (255 - g) * tint_factor; b = b +
					 * (255 - b) * tint_factor; } else if (deg >= 30 && deg <
					 * 90) { // yellow } else if (deg >= 90 && deg < 150) { //
					 * green r = r + (255 - r) * tint_factor; g = g + (255 - g)
					 * * tint_factor; b = b + (255 - b) * tint_factor; } else if
					 * (deg >= 150 && deg < 210) { // cyan } else if (deg >= 210
					 * && deg < 270) { // blue r = r + (255 - r) * tint_factor;
					 * g = g + (255 - g) * tint_factor; b = b + (255 - b) *
					 * tint_factor; } else if (deg >= 270 && deg < 330) { //
					 * magenta } else { // red r = r + (255 - r) * tint_factor;
					 * g = g + (255 - g) * tint_factor; b = b + (255 - b) *
					 * tint_factor; }
					 */
				}

				// set new RGB value
				p = (a << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;

				mimg.setRGB(x, y, p);
			}
		}

		return mimg;
	}

	private BufferedImage processImageskin(BufferedImage img, double tint_factor) {

		int width = img.getWidth();
		int height = img.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);
		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				double r = (p >> 16) & 0xff;
				double g = (p >> 8) & 0xff;
				double b = p & 0xff;

				float hsb[] = new float[3];
				Color.RGBtoHSB((int) r, (int) g, (int) b, hsb);

				if (hsb[1] < 0.1 && hsb[2] > 0.9) {
					// nearlyWhite
				} else if (hsb[2] < 0.1) {
					// nearlyBlack
				} else {
					float deg = hsb[0] * 360;

					if ((deg >= 0 && deg < 30) || (deg > 355)) {
						// red
						// r = r + (255 - r) * tint_factor;
						// g = g + (255 - g) * tint_factor;
						// b = b + (255 - b) * tint_factor;
					} else {
						// turn to grey scale
						// calculate tr, tg, tb
						double f = (0.2126 * r + 0.7152 * g + 0.0722 * b);
						int tr = (int) f;

						r = tr > 255 ? 255 : tr;
						g = tr > 255 ? 255 : tr;
						b = tr > 255 ? 255 : tr;

					}

					/*
					 * if (deg >= 0 && deg < 30) { // red r = r + (255 - r) *
					 * tint_factor; g = g + (255 - g) * tint_factor; b = b +
					 * (255 - b) * tint_factor; } else if (deg >= 30 && deg <
					 * 90) { // yellow } else if (deg >= 90 && deg < 150) { //
					 * green r = r + (255 - r) * tint_factor; g = g + (255 - g)
					 * * tint_factor; b = b + (255 - b) * tint_factor; } else if
					 * (deg >= 150 && deg < 210) { // cyan } else if (deg >= 210
					 * && deg < 270) { // blue r = r + (255 - r) * tint_factor;
					 * g = g + (255 - g) * tint_factor; b = b + (255 - b) *
					 * tint_factor; } else if (deg >= 270 && deg < 330) { //
					 * magenta } else { // red r = r + (255 - r) * tint_factor;
					 * g = g + (255 - g) * tint_factor; b = b + (255 - b) *
					 * tint_factor; }
					 */
				}

				// set new RGB value
				p = (a << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;

				mimg.setRGB(x, y, p);
			}
		}

		return mimg;
	}

	private BufferedImage blur(BufferedImage image, int[] filter, int filterWidth) {
		if (filter.length % filterWidth != 0) {
			throw new IllegalArgumentException("filter contains a incomplete row");
		}

		final int width = image.getWidth();
		final int height = image.getHeight();
		final int sum = IntStream.of(filter).sum();

		int[] input = image.getRGB(0, 0, width, height, null, 0, width);

		int[] output = new int[input.length];

		final int pixelIndexOffset = width - filterWidth;
		final int centerOffsetX = filterWidth / 2;
		final int centerOffsetY = filter.length / filterWidth / 2;

		// apply filter
		for (int h = height - filter.length / filterWidth + 1, w = width - filterWidth + 1, y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int r = 0;
				int g = 0;
				int b = 0;
				for (int filterIndex = 0, pixelIndex = y * width
						+ x; filterIndex < filter.length; pixelIndex += pixelIndexOffset) {
					for (int fx = 0; fx < filterWidth; fx++, pixelIndex++, filterIndex++) {
						int col = input[pixelIndex];
						int factor = filter[filterIndex];

						// sum up color channels seperately
						r += ((col >>> 16) & 0xFF) * factor;
						g += ((col >>> 8) & 0xFF) * factor;
						b += (col & 0xFF) * factor;
					}
				}
				r /= sum;
				g /= sum;
				b /= sum;
				// combine channels with full opacity
				output[x + centerOffsetX + (y + centerOffsetY) * width] = (r << 16) | (g << 8) | b | 0xFF000000;
			}
		}

		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		result.setRGB(0, 0, width, height, output, 0, width);
		return result;
	}

	public BufferedImage processSelectiveColor(BufferedImage img, ImageProcessForm imageProcessForm) {

		int width = img.getWidth();
		int height = img.getHeight();
		log.debug("Image provided:Width::{}", width);
		log.debug("Image provided:Height::{}", height);

		Map<String, Integer> selectedDominantColors = new HashMap();
		DominantColor domcol = new DominantColor();

		if (!imageProcessForm.getX1().isEmpty()) {
			selectedDominantColors.put(domcol.getDominantColor(img, Integer.parseInt(imageProcessForm.getX1()),
					Integer.parseInt(imageProcessForm.getY1()), Integer.parseInt(imageProcessForm.getW()),
					Integer.parseInt(imageProcessForm.getH())), 1);
		}

		if (!imageProcessForm.getX11().isEmpty()) {
			selectedDominantColors.put(domcol.getDominantColor(img, Integer.parseInt(imageProcessForm.getX11()),
					Integer.parseInt(imageProcessForm.getY11()), Integer.parseInt(imageProcessForm.getW1()),
					Integer.parseInt(imageProcessForm.getH1())), 1);
		}

		if (!imageProcessForm.getX12().isEmpty()) {
			selectedDominantColors.put(domcol.getDominantColor(img, Integer.parseInt(imageProcessForm.getX12()),
					Integer.parseInt(imageProcessForm.getY12()), Integer.parseInt(imageProcessForm.getW2()),
					Integer.parseInt(imageProcessForm.getH2())), 1);
		}		
		
		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);
				String color = domcol.getColorofPixel(p);
				if (selectedDominantColors.get(color) != null) {
					// leave

				} else {
					// turn to grey scale
					// calculate tr, tg, tb
					int a = (p >> 24) & 0xff;
					double r = (p >> 16) & 0xff;
					double g = (p >> 8) & 0xff;
					double b = p & 0xff;
					double f = (0.2126 * r + 0.7152 * g + 0.0722 * b);
					int tr = (int) f;

					r = tr > 255 ? 255 : tr;
					g = tr > 255 ? 255 : tr;
					b = tr > 255 ? 255 : tr;
					// set new RGB value
					p = (a << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;

				}

				mimg.setRGB(x, y, p);
			}

		}

		return mimg;
	}

}
