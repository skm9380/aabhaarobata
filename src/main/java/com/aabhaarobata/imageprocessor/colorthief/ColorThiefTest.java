/*
 * Java Color Thief
 * by Sven Woltmann, Fonpit AG
 * 
 * http://www.androidpit.com
 * http://www.androidpit.de
 *
 * License
 * -------
 * Creative Commons Attribution 2.5 License:
 * http://creativecommons.org/licenses/by/2.5/
 *
 * Thanks
 * ------
 * Lokesh Dhakar - for the original Color Thief JavaScript version
 * available at http://lokeshdhakar.com/projects/color-thief/
 */

package com.aabhaarobata.imageprocessor.colorthief;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.aabhaarobata.imageprocessor.colorthief.MMCQ.CMap;
import com.aabhaarobata.imageprocessor.colorthief.MMCQ.VBox;



public class ColorThiefTest {

    public static void main(String[] args) throws IOException {
        ColorThiefTest test = new ColorThiefTest();

        test.test("examples/img/photo1.jpg");
        test.test("examples/img/photo2.jpg");
        test.test("examples/img/photo3.jpg");

        test.saveToHTMLFile("examples-test.html");

        System.out.println("Finished.");
    }

    private StringBuilder sb;

    public ColorThiefTest() {
        sb = new StringBuilder();
        printStyleHeader();
    }

    /**
     * Prints a style header.
     */
    private void printStyleHeader() {
        sb.append(
                "<style>div.color{width:4em;height:4em;float:left;margin:0 1em 1em 0;}"
                        + "th{text-align:left}"
                        + "td{vertical-align:top;padding-right:1em}</style>");
    }

    /**
     * Tests the color thief with the image at the given path name.
     * 
     * @param pathname
     *            the image path name
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    public void test(String pathname) throws IOException {
        System.out.println("Analyzing image " + pathname + "...");

        sb.append("<h1>Image: &quot;").append(pathname).append("&quot</h1>");
        sb.append("<p><img src=\"").append(pathname).append("\" style=\"max-width:100%\"></p>");

        BufferedImage img = ImageIO.read(new File(pathname));

        // The dominant color is taken from a 5-map
        sb.append("<h2>Dominant Color</h2>");
        CMap result = ColorThief.getColorMap(img, 5);
        VBox dominantColor = result.vboxes.get(0);
        printVBox(dominantColor);

        // Get the full palette
        sb.append("<h2>Palette</h2>");
        result = ColorThief.getColorMap(img, 10);
        for (VBox vbox : result.vboxes) {
            printVBox(vbox);
        }
    }

    /**
     * Prints HTML code for a VBox.
     * 
     * @param vbox
     *            the vbox
     */
    private void printVBox(VBox vbox) {
        int[] rgb = vbox.avg(false);

        // Create color String representations
        String rgbString = createRGBString(rgb);
        String rgbHexString = createRGBHexString(rgb);

        sb.append("<div>");

        // Print color box
        sb
                .append("<div class=\"color\" style=\"background:") //
                .append(rgbString)
                .append(";\"></div>");

        // Print table with color code and VBox information
        sb.append(
                "<table><tr><th>Color code:</th>" //
                        + "<th>Volume &times pixel count:</th>" //
                        + "<th>VBox:</th></tr>");

        // Color code
        sb
                .append("<tr><td>") //
                .append(rgbString)
                .append(" / ")
                .append(rgbHexString)
                .append("</td>");

        // Volume / pixel count
        int volume = vbox.volume(false);
        int count = vbox.count(false);
        sb
                .append("<td>")
                .append(String.format("%,d", volume))
                .append(" &times; ")
                .append(String.format("%,d", count))
                .append(" = ")
                .append(String.format("%,d", volume * count))
                .append("</td>");

        // VBox
        sb
                .append("<td>") //
                .append(vbox.toString())
                .append("</td></tr></table>");

        // Stop floating
        sb.append("<div style=\"clear:both\"></div>");

        sb.append("</div>");
    }

    /**
     * Creates a string representation of an RGB array.
     * 
     * @param rgb
     *            the RGB array
     * 
     * @return the string representation
     */
    private String createRGBString(int[] rgb) {
        return "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")";
    }

    /**
     * Creates an HTML hex color code for the given RGB array (e.g. <code>#ff0000</code> for red).
     * 
     * @param rgb
     *            the RGB array
     * 
     * @return the HTML hex color code
     */
    private String createRGBHexString(int[] rgb) {
        String rgbHex = Integer.toHexString(rgb[0] << 16 | rgb[1] << 8 | rgb[2]);

        // Left-pad with 0s
        int length = rgbHex.length();
        if (length < 6) {
            rgbHex = "00000".substring(0, 6 - length) + rgbHex;
        }

        return "#" + rgbHex;
    }

    /**
     * Saves the test results in an HTML file.
     * 
     * @param fileName
     *            the file name
     */
    public void saveToHTMLFile(String fileName) {
        System.out.println("Saving HTML file...");
        try {
            Files.write(Paths.get(fileName), sb.toString().getBytes());
        } catch (IOException ex) {
            System.out.println("Error saving HTML file: " + ex.getMessage());
        }
    }

}
