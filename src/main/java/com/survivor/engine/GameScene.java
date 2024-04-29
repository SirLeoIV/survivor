package com.survivor.engine;

import com.survivor.engine.entities.Banner;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.entities.Overlay;
import com.survivor.engine.events.*;
import com.survivor.engine.listener.CollisionListener;
import com.survivor.engine.listener.GameListener;
import com.survivor.engine.listener.InputListener;
import com.survivor.engine.math.Vector2D;
import com.survivor.game.entities.Player;
import com.survivor.game.overlays.GameOverScreen;
import com.survivor.game.overlays.UpgradeScreen;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.HashMap;

public class GameScene extends Parent {

    Overlay overlay = null;

    private boolean gameLoopRunning = true;

    ArrayList<GameListener> gameListeners = new ArrayList<>();
    ArrayList<CollisionListener> collisionListeners = new ArrayList<>();
    ArrayList<InputListener> inputListeners = new ArrayList<>();
    Player player;
    Vector2D mousePosition = new Vector2D(0,0);

    HashMap<String, ArrayList<Entity>> entityDictionary = new HashMap<>(); // all entities grouped by class

    private static GameScene instance = new GameScene();

    public static GameScene getInstance() {
        return instance;
    }

    private GameScene() {}

    public static void setInstance(GameScene scene) {
        instance = scene;
    }

    public static boolean isGameLoopRunning() {
        return instance.gameLoopRunning;
    }

    public static void setGameLoopRunning(boolean gameLoopRunning) {
        instance.gameLoopRunning = gameLoopRunning;
    }

    public static void removeAllEntities() {
        ArrayList<Entity> entities = getAllEntities();
        ArrayList<Entity> entitiesCopy = (entities == null) ? new ArrayList<>() : new ArrayList<>(entities);
        for (Entity entity : entitiesCopy) {
            removeEntity(entity);
        }
    }

    public static ArrayList<Entity> getAllEntities() {
        return instance.entityDictionary.get(Entity.class.getName());
    }

    private void initializeInputListeners() {
        getScene().setOnKeyPressed(event -> {
            // if (!gameLoopRunning) return;
            InputKey key = InputKey.getKey(event.getCode().getName());
            if (key == InputKey.UNDEFINED) return;
            InputType type = KeyStates.keyStates.get(key) == InputType.KEY_RELEASED ? InputType.KEY_JUST_PRESSED : InputType.KEY_PRESSED;
            InputEvent inputEvent = new InputEvent(type.name(), type, key);
            KeyStates.keyStates.put(inputEvent.key, inputEvent.type);
            for (InputListener listener : inputListeners) {
                listener.inputUpdate(inputEvent);
            }
            if (key == InputKey.KEY_0) { // TODO test case
                if (getOverlay() != null) removeOverlay(getOverlay());
                else setOverlay(new UpgradeScreen(500, 500));
            }

            if (key == InputKey.KEY_9) { // TODO test case
                if (getOverlay() != null) removeOverlay(getOverlay());
                else setOverlay(new GameOverScreen(500, 300));
            }
            if (key == InputKey.KEY_B) { // TODO test case
                Banner banner = new Banner("Hello", 5000);
            }
        });
        getScene().setOnKeyReleased(event -> {
            if (!gameLoopRunning) return;
            InputKey key = InputKey.getKey(event.getCode().getName());
            if (key == InputKey.UNDEFINED) return;
            InputEvent inputEvent = new InputEvent("KEY_RELEASED", InputType.KEY_RELEASED, key);
            KeyStates.keyStates.put(inputEvent.key, inputEvent.type);
            for (InputListener listener : inputListeners) {
                listener.inputUpdate(inputEvent);
            }
        });
        getScene().setOnMousePressed(event -> {
            if (!gameLoopRunning) return;
            InputKey key = InputKey.getKey(event.getButton().name());
            if (key == InputKey.UNDEFINED) return;
            InputType type = KeyStates.keyStates.get(key) == InputType.MOUSE_RELEASED ? InputType.MOUSE_JUST_PRESSED : InputType.MOUSE_PRESSED;
            InputEvent inputEvent = new InputEvent(type.name(), type, key);
            KeyStates.keyStates.put(inputEvent.key, inputEvent.type);
            for (InputListener listener : inputListeners) {
                listener.inputUpdate(inputEvent);
            }
        });
        getScene().setOnMouseReleased(event -> {
            if (!gameLoopRunning) return;
            InputKey key = InputKey.getKey(event.getButton().name());
            InputEvent inputEvent = new InputEvent("MOUSE_RELEASED", InputType.MOUSE_RELEASED, key);
            if (key == InputKey.UNDEFINED) return;
            KeyStates.keyStates.put(inputEvent.key, inputEvent.type);
            for (InputListener listener : inputListeners) {
                listener.inputUpdate(inputEvent);
            }
        });
        getScene().setOnScroll(event -> {
            if (!gameLoopRunning) return;
            InputEvent inputEvent = new InputEvent("MOUSE_SCROLL",
                    event.getDeltaY() > 0 ?
                            InputType.MOUSE_SCROLL_UP : InputType.MOUSE_SCROLL_DOWN,
                    event.getDeltaY() > 0 ?
                            InputKey.getKey("MOUSE_SCROLL_UP") : InputKey.getKey("MOUSE_SCROLL_DOWN"));
            if (inputEvent.key == InputKey.UNDEFINED) return;
            for (InputListener listener : inputListeners) {
                listener.inputUpdate(inputEvent);
            }
        });
        getScene().setOnMouseMoved(event -> {
            mousePosition = new Vector2D(event.getX(), event.getY());
        });
    }

    public static void addEntity(Entity entity) {
        boolean reachedEnd = false;
        String className = entity.getClass().getName();

        while (!reachedEnd) {
            if (!instance.entityDictionary.containsKey(className)) {
                instance.entityDictionary.put(className, new ArrayList<>());
            }
            instance.entityDictionary.get(className).add(entity);
            if (className.equals(Entity.class.getName())) {
                reachedEnd = true;
            }
            try {
                className = Class.forName(className).getSuperclass().getName();
            } catch (ClassNotFoundException e) {
                reachedEnd = true;
                e.printStackTrace();
            }
        }
        instance.getChildren().add(entity);
    }

    public static void removeEntityV2(Entity entity) {
        boolean reachedEnd = false;
        String className = entity.getClass().getName();

        while (!reachedEnd) {
            if (instance.entityDictionary.containsKey(className)) {
                instance.entityDictionary.get(className).remove(entity);
            }
            if (className.equals(Entity.class.getName())) {
                reachedEnd = true;
            }
            try {
                className = Class.forName(className).getSuperclass().getName();
            } catch (ClassNotFoundException e) {
                reachedEnd = true;
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Entity> getEntitiesByClass(Class<?> clazz) {
        ArrayList<Entity> result =  instance.entityDictionary.get(clazz.getName());
        return result == null ? new ArrayList<>() : result;
    }

    public static void setOverlay(Overlay overlay) {
        setGameLoopRunning(false);
        instance.getChildren().remove(instance.overlay);
        instance.overlay = overlay;
        instance.getChildren().add(overlay);
        notifyGameListeners(new GameEvent(GameEventType.GAME_PAUSE, overlay));
    }

    public static Overlay getOverlay() {
        return instance.overlay;
    }

    public static void removeOverlay(Overlay overlay) {
        if (instance.overlay != overlay) return;
        instance.getChildren().remove(instance.overlay);
        instance.overlay = null;
        setGameLoopRunning(true);
        notifyGameListeners(new GameEvent(GameEventType.GAME_RESUME, overlay));
    }

    public static void notifyGameListeners(GameEvent event) {
        for (GameListener listener : instance.gameListeners) {
            listener.gameUpdate(event);
        }
    }

    public static void notifyCollisionListeners(CollisionEvent event) {
        ArrayList<CollisionListener> listeners = new ArrayList<>(instance.collisionListeners);
        for (CollisionListener listener : listeners) {
            listener.collisionUpdate(event);
        }
    }

    public static void removeEntity(Entity entity) {
        notifyGameListeners(new GameEvent(GameEventType.REMOVE_ENTITY, entity));
        instance.getChildren().remove(entity);
        removeEntityV2(entity);

        removeAllSubscriptions(entity);
        entity.stopTimer();
    }

    private static void removeAllSubscriptions(Entity entity) {
        detachGameListener(entity);

        if (entity instanceof CollisionListener) {
            detachCollisionListener((CollisionListener) entity);
        }
        if (entity instanceof InputListener) {
            detachInputListener((InputListener) entity);
        }
    }

    public static void attachGameListener(GameListener listener) {
        instance.gameListeners.add(listener);
        notifyGameListeners(new GameEvent(GameEventType.ATTACH_LISTENER, listener));
    }

    public static void detachGameListener(GameListener listener) {
        instance.gameListeners.remove(listener);
        notifyGameListeners(new GameEvent(GameEventType.DETACH_LISTENER, listener));
    }

    public static void attachCollisionListener(CollisionListener listener) {
        instance.collisionListeners.add(listener);
    }

    public static void detachCollisionListener(CollisionListener listener) {
        instance.collisionListeners.remove(listener);
    }

    public static void attachInputListener(InputListener inputListener) {
        instance.inputListeners.add(inputListener);
    }

    public static void detachInputListener(InputListener inputListener) {
        instance.inputListeners.remove(inputListener);
    }

    public void initialize() {
        initializeInputListeners();
    }

    public static void setPlayer(Player player) {
        instance.player = player;
    }

    public static Player getPlayer() {
        return instance.player;
    }

    public static Vector2D getMousePosition() {
        return instance.mousePosition;
    }
}
