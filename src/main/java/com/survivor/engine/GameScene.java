package com.survivor.engine;

import com.survivor.engine.entities.Character;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.*;
import com.survivor.engine.listener.CollisionListener;
import com.survivor.engine.listener.GameListener;
import com.survivor.engine.listener.InputListener;
import com.survivor.engine.math.Vector2D;
import javafx.scene.Parent;

import java.util.ArrayList;

public class GameScene extends Parent {

    ArrayList<Entity> entities = new ArrayList<>();
    ArrayList<Entity> characters = new ArrayList<>();
    ArrayList<GameListener> gameListeners = new ArrayList<>();
    ArrayList<CollisionListener> collisionListeners = new ArrayList<>();
    ArrayList<InputListener> inputListeners = new ArrayList<>();
    Entity player;
    Vector2D mousePosition = new Vector2D(0,0);

    private static GameScene instance = new GameScene();

    public static GameScene getInstance() {
        return instance;
    }

    private GameScene() {}

    public static void setInstance(GameScene scene) {
        instance = scene;
    }

    public static ArrayList<Entity> getEntities() {
        return instance.entities;
    }

    private void initializeInputListeners() {
        getScene().setOnKeyPressed(event -> {
            InputKey key = InputKey.getKey(event.getCode().getName());
            if (key == InputKey.UNDEFINED) return;
            InputType type = KeyStates.keyStates.get(key) == InputType.KEY_RELEASED ? InputType.KEY_JUST_PRESSED : InputType.KEY_PRESSED;
            InputEvent inputEvent = new InputEvent(type.name(), type, key);
            KeyStates.keyStates.put(inputEvent.key, inputEvent.type);
            for (InputListener listener : inputListeners) {
                listener.inputUpdate(inputEvent);
            }
        });
        getScene().setOnKeyReleased(event -> {
            InputKey key = InputKey.getKey(event.getCode().getName());
            if (key == InputKey.UNDEFINED) return;
            InputEvent inputEvent = new InputEvent("KEY_RELEASED", InputType.KEY_RELEASED, key);
            KeyStates.keyStates.put(inputEvent.key, inputEvent.type);
            for (InputListener listener : inputListeners) {
                listener.inputUpdate(inputEvent);
            }
        });
        getScene().setOnMousePressed(event -> {
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
            InputKey key = InputKey.getKey(event.getButton().name());
            InputEvent inputEvent = new InputEvent("MOUSE_RELEASED", InputType.MOUSE_RELEASED, key);
            if (key == InputKey.UNDEFINED) return;
            KeyStates.keyStates.put(inputEvent.key, inputEvent.type);
            for (InputListener listener : inputListeners) {
                listener.inputUpdate(inputEvent);
            }
        });
        getScene().setOnScroll(event -> {
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

    public static void addEntity(Entity entity) {
        instance.entities.add(entity);
        instance.getChildren().add(entity);
    }

    public static void removeEntity(Entity entity) {
        instance.entities.remove(entity);
        instance.getChildren().remove(entity);

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

    public static void addCharacter(Entity character) {
        instance.characters.add(character);
        instance.entities.add(character);
        instance.getChildren().add(character);
    }

    public static void removeCharacter(Entity character) {
        instance.characters.remove(character);
        instance.entities.remove(character);
        instance.getChildren().remove(character);
    }

    public static void attachGameListener(GameListener listener) {
        instance.gameListeners.add(listener);
        notifyGameListeners(new GameEvent("ATTACH", listener));
    }

    public static void detachGameListener(GameListener listener) {
        instance.gameListeners.remove(listener);
        notifyGameListeners(new GameEvent("DETACH", listener));
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

    public static void setPlayer(Entity player) {
        instance.player = player;
    }

    public static Entity getPlayer() {
        return instance.player;
    }

    public static Vector2D getMousePosition() {
        return instance.mousePosition;
    }
}
