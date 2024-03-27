import sys
from PySide6.QtWidgets import QApplication

from MainWindow import MainWindow

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MainWindow()
    window.setGeometry(100, 100, 800, 500)  # Устанавливаем размеры окна
    window.show()
    sys.exit(app.exec())
