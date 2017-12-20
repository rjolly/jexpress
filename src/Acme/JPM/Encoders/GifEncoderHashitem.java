package Acme.JPM.Encoders;


class GifEncoderHashitem {

	public int rgb;
	public int count;
	public int index;
	public boolean isTransparent;

	public GifEncoderHashitem(int i, int j, int k, boolean flag) {
		rgb = i;
		count = j;
		index = k;
		isTransparent = flag;
	}
}
