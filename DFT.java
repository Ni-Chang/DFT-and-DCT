package homework2;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;

public class DFT {
	Image im;
	BufferedImage imageAuth = null;
	int iw;
	int ih;
	int[] pixels;
	int[] newPixels;

	public DFT(BufferedImage img) throws InterruptedException {
		this.im = img;
		this.iw = im.getWidth(null);
		this.ih = im.getHeight(null);
		pixels = new int[iw * ih];
		convert();
	}
	
	public BufferedImage getImg() {
		return imageAuth;
	}

	private void convert() throws InterruptedException {
		PixelGrabber pg = new PixelGrabber(im, 0, 0, iw, ih, pixels, 0, iw);
		pg.grabPixels();
		int w = 1;
		int h = 1;
		while (w * 2 <= iw) {
			w *= 2;
		}
		while (h * 2 <= ih) {
			h *= 2;
		}
		Complex[] src = new Complex[h * w];
		Complex[] dest = new Complex[h * w];
		newPixels = new int[h * w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				newPixels[i * w + j] = pixels[i * iw + j] & 0xff;
			}
		}
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				dest[i * w + j] = new Complex();
				src[i * w + j] = new Complex(newPixels[i * w + j], 0);
			}
		}
		for (int i = 0; i < h; i++) {
			FFT.fft(src, i, w, dest);
		}
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				src[j * h + i] = dest[i * w + j];
			}
		}
		for (int i = 0; i < w; i++) {
			FFT.fft(src, i, h, dest);
		}
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				double re = dest[j * h + i].re;
				double im = dest[j * h + i].im;
				int ii = 0, jj = 0;
				int temp = (int) (Math.sqrt(re * re + im * im) / 100);
				if (temp > 255) {
					temp = 255;
				}
				if (i < h / 2) {
					ii = i + h / 2;
				} else {
					ii = i - h / 2;
				}
				if (j < w / 2) {
					jj = j + w / 2;
				} else {
					jj = j - w / 2;
				}
				newPixels[ii * w + jj] = temp;
			}
		}
		imageAuth = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		ColorModel colorModel = imageAuth.getColorModel();
		WritableRaster raster = colorModel.createCompatibleWritableRaster(w, h);
		raster.setPixels(0, 0, w, h, newPixels);
		imageAuth.setData(raster);
	}
}
