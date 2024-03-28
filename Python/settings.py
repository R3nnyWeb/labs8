# Размер сцены
SCENE_WIDTH = 2000
SCENE_HEIGHT = 1000

# Расстояние между точками
GRID_SPACING = 15

POSITION_SIZE = 5

def rounded(coord):
   return round(coord / GRID_SPACING) * GRID_SPACING