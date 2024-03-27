import math

from PySide6.QtGui import Qt
from PySide6.QtWidgets import QWidget, QVBoxLayout, QLabel, QLineEdit, QPushButton, QGraphicsScene, \
    QGraphicsView, QInputDialog, QGraphicsRectItem

from Element import Element
from PositionSquare import PositionSquare
from WaveAlg import WaveAlg


class MainWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.grid = None
        self.selected_positions = []
        self.setWindowTitle("Отрисовка позиций и элементов")
        layout = QVBoxLayout()

        # Создаем вертикальный лейаут для ввода количества элементов и кнопки
        input_layout = QVBoxLayout()
        self.label = QLabel("Введите количество элементов:")
        input_layout.addWidget(self.label)
        self.input_text = QLineEdit()
        self.input_text.setFixedSize(200, 30)
        input_layout.addWidget(self.input_text)
        layout.addLayout(input_layout)

        # Создаем кнопку "Отрисовать"
        self.draw_button = QPushButton("Отрисовать")
        self.draw_button.setFixedSize(200, 30)
        self.draw_button.clicked.connect(self.draw_positions)
        layout.addWidget(self.draw_button)

        # Создаем сцену и вид
        self.scene = QGraphicsScene()
        self.view = QGraphicsView(self.scene)
        self.view.setFixedSize(600, 400)  # Устанавливаем фиксированный размер для виджета
        layout.addWidget(self.view)

        # Устанавливаем стиль границы для виджета
        self.view.setStyleSheet("border: 2px solid blue")

        self.setLayout(layout)

        self.elements = []  # Хранилище элементов
        self.positions = []  # Хранилище позиций
        self.adjacency_matrix = []  # Матрица смежности
        self.grid = None  # Массив для хранения значений сетки
        self.grid_cell_size = 4  # Размер ячейки сетки
        self.grid_margin = 4  # Отступ от края окна до сетки
        self.init_grid()
        self.wave_alg = WaveAlg(self.grid, self)

    def init_grid(self):
        # Создаем сетку при инициализации окна
        window_width = self.width()
        window_height = self.height()

        # Определяем количество ячеек сетки по горизонтали и вертикали
        num_horizontal_cells = window_width // self.grid_cell_size
        num_vertical_cells = window_height // self.grid_cell_size

        # Инициализируем массив сетки
        self.grid = [[0] * num_vertical_cells for _ in range(num_horizontal_cells)]

    def draw_grid(self):
        # Отрисовываем сетку на сцене
        for i in range(len(self.grid)):
            for j in range(len(self.grid[0])):
                self.draw_cell(i, j)

    def draw_cell(self, i, j):
        x = i * self.grid_cell_size
        y = j * self.grid_cell_size
        rect_item = QGraphicsRectItem(x, y, self.grid_cell_size, self.grid_cell_size)
        if self.grid[i][j] == -2:
            rect_item.setBrush(Qt.black)
            self.scene.addItem(rect_item)
        # elif self.grid[i][j] == 0:
        #     rect_item.setBrush(Qt.gray)
        #     self.scene.addItem(rect_item)

    def draw_positions(self):
        try:
            n = int(self.input_text.text())
        except ValueError:
            return

        self.adjacency_matrix = [[0] * (n + 1) for _ in range(n + 1)]
        self.elements = [Element(i + 1) for i in range(n)]
        self.positions = []

        # Очищаем сцену перед отрисовкой
        self.scene.clear()

        size = 50
        margin = 50  # отступ между позициями
        max_positions_in_row = int(math.sqrt(n))  # максимальное количество позиций в столбце
        rows = math.ceil(n / max_positions_in_row)  # вычисляем количество столбцов
        cols = min(max_positions_in_row, n)  # вычисляем количество строк

        # Вычисляем размер сетки
        grid_width = cols * (size + margin) - margin
        grid_height = rows * (size + margin) - margin

        # Начальные координаты для отрисовки
        start_x = (self.view.width() - grid_width) / 2
        start_y = (self.view.height() - grid_height) / 2

        # Отрисовываем позиции сеткой
        position_index = 0
        for col in range(cols):
            for row in range(rows):
                if position_index >= n:
                    break
                x = start_x + row * (size + margin)
                y = start_y + col * (size + margin)
                element = self.elements[position_index]
                position_square = PositionSquare(position_index + 1, x, y, element, self, size)
                position_index += 1
                element.set_position(position_square)
                self.scene.addItem(position_square)
                self.positions.append(position_square)

                for i in range(round(x - self.grid_margin), round(x - self.grid_margin + size)):
                    for j in range(round(y - self.grid_margin), round(y - self.grid_margin + size)):
                        grid_x = i // self.grid_cell_size
                        grid_y = j // self.grid_cell_size
                        # Помечаем соответствующую ячейку сетки как пересекаемую позицией
                        self.grid[grid_x + 1][grid_y + 1] = -1

        self.draw_grid()

    def create_connection(self, position):
        if self.selected_positions.__contains__(position):
            return
        self.selected_positions.append(position)
        if len(self.selected_positions) == 2:
            pos1: PositionSquare = self.selected_positions[0]
            el1: Element = pos1.element
            pos2: PositionSquare = self.selected_positions[1]
            el2: Element = pos2.element
            weight, ok = QInputDialog.getInt(self, "Введите вес связи", "Вес связи:")

            if ok:
                self.adjacency_matrix[el1.number][el2.number] = weight
                self.adjacency_matrix[el2.number][el1.number] = weight

                start_x, start_y = pos1.left_x, pos1.left_y
                end_x, end_y = pos2.left_x, pos2.left_y

                (start_dir, end_dir) = determine_path_direction(start_x, start_y, end_x, end_y)

                (grid_start_x, grid_start_y) = self.determine_coords(start_x, start_y, start_dir)
                (grid_end_x, grid_end_y) = self.determine_coords(end_x, end_y, end_dir)



                if self.wave_alg.find_path((grid_start_x, grid_start_y), (grid_end_x, grid_end_y)):
                    print("успех")
                else:
                    print("Путь не найден")
            self.selected_positions = []

    def determine_coords(self, x, y, direct):
        target_x = x
        target_y = y
        if direct == Direction.LEFT or direct == Direction.RIGHT:
            target_y = y + 25
        if direct == Direction.UP or direct == Direction.DOWN:
            target_x = x + 25

        if direct == Direction.DOWN:
            target_y = target_y + 50
        if direct == Direction.RIGHT:
            target_x = target_x + 50

        grid_x = round(target_x // self.grid_cell_size)
        grid_y = round(target_y // self.grid_cell_size)

        if direct == Direction.LEFT:
            grid_x = grid_x - 2
        elif direct == Direction.UP:
            grid_y = grid_y - 2
        elif direct == Direction.DOWN:
            grid_y = grid_y + 2
        if direct == Direction.RIGHT:
            grid_x = grid_x + 2

        return grid_x, grid_y


from enum import Enum


class Direction(Enum):
    LEFT = 1
    RIGHT = 2
    UP = 3
    DOWN = 4


def determine_path_direction(start_x, start_y, end_x, end_y):
    horizontal_diff = abs(end_x - start_x)
    vertical_diff = abs(end_y - start_y)
    if horizontal_diff > vertical_diff:
        if start_x > end_x:
            return Direction.LEFT, Direction.RIGHT
        else:
            return Direction.RIGHT, Direction.LEFT
    else:
        if start_y > end_y:
            return Direction.UP, Direction.DOWN
        else:
            return Direction.DOWN, Direction.UP


