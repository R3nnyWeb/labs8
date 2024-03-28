from PySide6.QtGui import QPainter, QPen, Qt
from PySide6.QtWidgets import QGraphicsView, QGraphicsScene

from settings import SCENE_WIDTH, SCENE_HEIGHT, GRID_SPACING


class Workspace(QGraphicsView):
    def __init__(self, parent=None):
        super(Workspace, self).__init__(parent)
        self.setScene(QGraphicsScene(self))
        self.setSceneRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT)  # Устанавливаем размер рабочей области
        self.setRenderHint(QPainter.Antialiasing)


        self.zoom_factor = 1.25  # Фактор масштабирования
        self.grid_spacing = 50  # Размер сетки
        self.min_zoom = 1
        self.max_zoom = 10.0

    def drawBackground(self, painter, rect):
        # Определение параметров для рисования сетки
        grid_spacing = GRID_SPACING
        pen = QPen(Qt.lightGray, 1, Qt.DotLine)
        painter.setPen(pen)

        # Рисование вертикальных линий сетки
        x = grid_spacing
        while x < SCENE_WIDTH:
            painter.drawLine(x, 0, x, SCENE_HEIGHT)
            x += grid_spacing

        # Рисование горизонтальных линий сетки
        y = grid_spacing
        while y < SCENE_HEIGHT:
            painter.drawLine(0, y, SCENE_WIDTH, y)
            y += grid_spacing  # Рисуем точки на пересечениях

    def wheelEvent(self, event):
        # Обработка события колеса мыши для масштабирования
        zoom_factor = 1.0 + event.angleDelta().y() / 1200.0
        current_zoom = self.transform().m11()  # Масштаб по оси X (и Y, так как у нас одинаковый масштаб)
        new_zoom = current_zoom * zoom_factor
        if self.min_zoom <= new_zoom <= self.max_zoom:
            self.scale(zoom_factor, zoom_factor)


