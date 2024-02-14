using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace LR1Noise
{
    public partial class Form1 : Form
    {
        Bitmap imInput;
        Bitmap imFirst;
        Bitmap result;
        public Form1()
        {
            InitializeComponent();
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            OpenFileDialog openDialog = new OpenFileDialog();
            //openDialog.Filter = "Файлы изображений|*.bmp;*.png;*.jpg";
            if (openDialog.ShowDialog() != DialogResult.OK)
                return;
            try
            {
                imInput = (Bitmap)Image.FromFile(openDialog.FileName);
            }
            catch (OutOfMemoryException ex)
            {
                MessageBox.Show("Ошибка чтения картинки");
                return;
            }
            imFirst = new Bitmap(imInput.Width, imInput.Height);
            for (int x = 0; x < imInput.Width; x++)
            {
                for (int y = 0; y < imInput.Height; y++)
                {
                    Color bitmapColor = imInput.GetPixel(x, y);

                    int colorGray = (int)(bitmapColor.R * 0.299 + bitmapColor.G * 0.587 + bitmapColor.B * 0.114);

                    imFirst.SetPixel(x, y, Color.FromArgb(colorGray, colorGray, colorGray));

                }

            }

            pictureBox1.Image = imFirst;
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            SaveFileDialog sfd = new SaveFileDialog();
            sfd.Filter = "Images|*.png;*.bmp;*.jpg";
            ImageFormat format = ImageFormat.Png;
            if (sfd.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                string ext = System.IO.Path.GetExtension(sfd.FileName);
                switch (ext)
                {
                    case ".jpg":
                        format = ImageFormat.Jpeg;
                        break;
                    case ".bmp":
                        format = ImageFormat.Bmp;
                        break;
                }
                pictureBox2.Image.Save(sfd.FileName, format);
            }
        }

        private int filter(Bitmap bitmap, int x, int y, int[,] mask)
        {
            int sum = 0;
            for (int i = -1; i < 2; i++)
            {
                for (int j = -1; j < 2; j++)
                {
                    int pixel = bitmap.GetPixel((x + i),y + j).R;
                    //int gray = ((pixel & 0x00FF0000 >> 0x10) + (pixel & 0x0000FF00 >> 0x8) + (pixel & 0x000000FF)) / 3;
                    sum += (pixel * mask[(i + 1),j + 1]);
                }
            }
            return sum;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            result = new Bitmap(imFirst.Width, imFirst.Height);
            int[,] Lx = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
            int[,] Ly = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };

            for (int i = 1; i<result.Width-1; i++) {
			for (int j = 1; j<result.Height - 1; j++) {
				int X = filter(imFirst, i, j, Lx);
                    int Y = filter(imFirst, i, j, Ly);
                    int Gxy = Convert.ToInt32(Math.Round(Math.Sqrt(X * X + Y * Y)));
                    if (Gxy > 255) Gxy = 255;
				else Gxy = 0;
				result.SetPixel(i, j, Color.FromArgb(Gxy, Gxy, Gxy));
			}
		}

            pictureBox2.Image = result;
        }

        private Bitmap filterNESW(Bitmap bitmap, int[,] mask)
        {
            result = new Bitmap(imFirst.Width, imFirst.Height);
            for (int i = 1; i < result.Width - 1; i++)
            {
                for (int j = 1; j < result.Height - 1; j++)
                {
                    int X = filter(imFirst, i, j, mask);
                    int Gxy = Convert.ToInt32(Math.Round(Math.Sqrt(X * X)));
                    if (Gxy > 255) Gxy = 255;
                    else Gxy = 0;
                    result.SetPixel(i, j, Color.FromArgb(Gxy, Gxy, Gxy));
                }
            }
            return result;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            int[,] N = { { 1, 1, 1 }, { 1, -2, 1 }, { -1, -1, -1 } };
            int[,] NE = { { 1, 1, 1 }, { -1, -2, 1 }, { -1, -1, 1 } };
            int[,] E = { { -1, 1, 1 }, { -1, -2, 1 }, { -1, 1, 1 } };
            int[,] SE = { { -1, -1, 1 }, { -1, -2, 1 }, { 1, 1, 1 } };
            int[,] S = { { -1, -1, -1 }, { 1, -2, 1 }, { 1, 1, 1 } };
            int[,] SW = { { 1, -1, -1 }, { 1, -2, -1 }, { 1, 1, 1 } };
            int[,] W = { { 1, 1, -1 }, { 1, -2, -1 }, { 1, 1, -1 } };
            int[,] NW = { { 1, 1, 1 }, { 1, -2, -1 }, { 1, -1, -1 } };
            Bitmap result = new Bitmap(imFirst);

            if (checkBox1.Checked)
            {
                result = filterNESW(result, N);
            }
            if (checkBox2.Checked)
            {
                result = filterNESW(result, NE);
            }
            if (checkBox3.Checked)
            {
                result = filterNESW(result, E);
            }
            if (checkBox4.Checked)
            {
                result = filterNESW(result, SE);
            }
            if (checkBox5.Checked)
            {
                result = filterNESW(result, S);
            }
            if (checkBox6.Checked)
            {
                result = filterNESW(result,SW);
            }
            if (checkBox7.Checked)
            {
                result = filterNESW(result, W);
            }
            if (checkBox8.Checked)
            {
                result = filterNESW(result, NW);
            }
            pictureBox2.Image = result;
        }

        private void checkBox3_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox3.Checked == false)
            {
                button2.Enabled = false;
            }
        }
    }
}
