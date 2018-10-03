package homework2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ImageProcessing {
	static BufferedImage image;
	private static Scanner input;
	private static Scanner in;
	private static Scanner in2;
	
	private static int welcomeMessage() {
		System.out.println("Choose process No.:");
		System.out.println("1 Fourier Transformer");
		System.out.println("2 Discrete Cos Transformer");
		System.out.println("3 Quit");
		input = new Scanner(System.in);
		int opt = input.nextInt();
		return opt;
	}
	
	private static void readFile() throws IOException {
		String filePath;
		System.out.println("Enter Image:");
		in = new Scanner(System.in);
		filePath = in.nextLine();
		File input = new File(filePath);
		image = ImageIO.read(input);
	}

	private static void outImage(BufferedImage output) throws IOException {
		String filePath;
		System.out.println("Output Image:");
		in2 = new Scanner(System.in);
		filePath = in2.nextLine();
		File file = new File(filePath);
		ImageIO.write(output, "jpg", file);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		int opt = welcomeMessage();
		while (opt != 3) {
			switch (opt) {
			case 1:
				readFile();
				DFT fourierTransImg = new DFT(image);
				BufferedImage fourierTransImage = fourierTransImg.getImg();
				outImage(fourierTransImage);
				System.out.println("Success！");
				break;
			case 2:
				readFile();
				DCT disCosTransImg = new DCT(image);
				BufferedImage disCosTransImage = disCosTransImg.getImg();
				outImage(disCosTransImage);
				System.out.println("Success！");
				break;
			default:
				System.out.println("Fail!");
			}
			opt = welcomeMessage();
		}
	}
}
