import sys

from PySide6.QtWidgets import QApplication, QMainWindow, QVBoxLayout, QPushButton, QWidget

from PositionItem import PositionItem
from Workspace import Workspace


class MainWindow(QMainWindow):
    def __init__(self):
        super(MainWindow, self).__init__()

        self.central_widget = QWidget()
        self.setCentralWidget(self.central_widget)

        self.workspace = Workspace(self.central_widget)

        self.init_ui()

    def init_ui(self):
        layout = QVBoxLayout()
        layout.addWidget(self.workspace)

        button = QPushButton("Добавить объект")
        button.clicked.connect(self.add_object)
        layout.addWidget(button)

        self.central_widget.setLayout(layout)

        self.setWindowTitle("Программа с рабочей областью")
        self.show()

    def add_object(self):
        item = PositionItem(100, 100, 1, 1)
        self.workspace.scene().addItem(item)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MainWindow()
    sys.exit(app.exec())
