package pl.crystalek.sokoban.controller.type;

import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.controller.GameModeChoiceController;
import pl.crystalek.sokoban.controller.MapEditorController;
import pl.crystalek.sokoban.controller.SelectLevelController;

public enum ExecutionSource {
    MAPEDITOR(MapEditorController.class),
    GAMEMODECHOICE(GameModeChoiceController.class),
    SELECTLEVEL(SelectLevelController.class);

    private final Class<? extends Controller> controllerClass;

    ExecutionSource(final Class<? extends Controller> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Class<? extends Controller> getControllerClass() {
        return controllerClass;
    }
}
