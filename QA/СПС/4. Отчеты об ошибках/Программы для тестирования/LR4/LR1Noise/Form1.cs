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
            openDialog.Filter = "Файлы изображений|*.bmp;*.png;*.jpg";
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


        private void button1_Click(object sender, EventArgs e)
        {
            result = new Bitmap(imFirst.Width, imFirst.Height);

            double xKoef = Convert.ToDouble(txtX.Text);
            double YKoef = Convert.ToDouble(txtY.Text);

            if (xKoef < 0) xKoef = 0;
            if (YKoef < 0) YKoef = 0;

            for (int i = 0; i < result.Width; i++)
            {
                for (int j = 0; j < result.Height; j++)
                {
                    int newX = (int)(i * xKoef);
                    int newY = (int)(j * YKoef);
                    
                    if (newX < result.Width && newY < result.Height)
                    {
                        result.SetPixel(newX, newY, imFirst.GetPixel(i, j));
                    } 
                }
            }

            pictureBox2.Image = result;
        }

         private void button2_Click(object sender, EventArgs e)
        {
            Bitmap result = new Bitmap(imFirst.Width*3, imFirst.Height*3);

            int angle = Convert.ToInt32(txtAngle.Text);
            double cos = Math.Cos(angle * Math.PI / 180.0);
            double sin = Math.Sin(angle * Math.PI / 180.0);

            for (int i = 0; i < imFirst.Width; i++)
            {
                for (int j = 0; j < imFirst.Height; j++)
                {

                    int newX = (int)(i * cos - j * sin) + (imFirst.Width);
                    int newY = (int)(i * sin + j * cos) + (imFirst.Height);

                    result.SetPixel(newX, newY, imFirst.GetPixel(i, j));

                }
            }

            for (int i = 0; i < result.Width; i++)
            {
                for (int j = 0; j < result.Height; j++)
                {
                    int r = result.GetPixel(i, j).R;
                    int b = result.GetPixel(i, j).B;
                    int g = result.GetPixel(i, j).G;
                    int a = result.GetPixel(i, j).A;
                    if (result.GetPixel(i, j).IsEmpty) { r = 255; }
                }
            }
            int XStart, YStart, XEnd, YEnd;
            if (angle > 0)
            {
                XEnd = (int)(imFirst.Width * cos) + (imFirst.Width);
                YStart = (int)(imFirst.Height);
                XStart = (int)(-imFirst.Height * sin) + (imFirst.Width);
                YEnd = (int)(imFirst.Width * sin + imFirst.Height * cos) + (imFirst.Height);
            }
            else
            {
                XStart = (int)(imFirst.Width);
                YStart = (int)(imFirst.Height * cos) + (imFirst.Height);
                XEnd = (int)(imFirst.Width * cos - imFirst.Height * sin) + (imFirst.Width);
                YEnd = (int)(imFirst.Width * sin) + (imFirst.Height);
            }
            
            Bitmap cutResult = result.Clone(new Rectangle(XStart, YStart, (XEnd-XStart), (YEnd-YStart)),result.PixelFormat);
            pictureBox2.Image = cutResult;
        }

         private void button3_Click(object sender, EventArgs e)
        {
            result = new Bitmap(imFirst.Width, imFirst.Height);

            double x0 = Convert.ToDouble(txtX0.Text);
            double Y0 = Convert.ToDouble(txtY0.Text);

            if (x0 < 0) x0 = 0;
            if (Y0 < 0) Y0 = 0;

            for (int i = 0; i < result.Width; i++)
            {
                for (int j = 0; j < result.Height; j++)
                {
                    int newX = (int)(i + x0);
                    int newY = (int)(j + Y0);

                    if (newX < result.Width && newY < result.Height)
                    {
                        result.SetPixel(newX, newY, imFirst.GetPixel(i, j));
                    }
                }
            }

            pictureBox2.Image = result;
        }
    }
}
