package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sun.org.apache.xpath.internal.operations.String;

import java.io.IOException;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    World world;
    GameObject plane;

    GameObject plane2;
    OrthographicCamera camera;
    SpriteBatch batch;

    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    public static java.lang.String ServerIP;
    public static java.lang.String ServerPort;


/****************************************************************************************************************/
    private Texture backgroundTexture;
    private Sprite backgroundSprite;
/****************************************************************************************************************/

/**/
    private GameClient client;

    public class GameClient {
        Client client;
        java.lang.String name;

        public GameClient() {
            client = new Client();
            client.start();
            NetworkEntities.register(client);
            client.addListener(new Listener.ThreadedListener(new Listener() {
                @Override
                public void connected(Connection connection) {}

                @Override
                public void received(Connection connection, Object object) {
                    if (object instanceof NetworkEntities.AddPlayer) {
                        NetworkEntities.AddPlayer msg = (NetworkEntities.AddPlayer) object;
                        //ui.addCharacter(msg.character);
                        return;
                    }

                    if (object instanceof NetworkEntities.UpdatePlayer) {
                        //ui.updateCharacter((UpdateCharacter)object);
                        return;
                    }

                    if (object instanceof NetworkEntities.RemovePlayer) {
                        NetworkEntities.RemovePlayer msg = (NetworkEntities.RemovePlayer) object;
                        //ui.removeCharacter(msg.id);
                        return;
                    }
                }

                @Override
                public void disconnected(Connection connection) {
                    System.exit(0);
                }
            }));

            try {
                client.connect(50000, ServerIP, NetworkEntities.port);
            } catch (IOException e) {
                e.printStackTrace();
            }

            NetworkEntities.Login login = new NetworkEntities.Login();
            login.name = "Test2";

            client.sendTCP(login);

        }

    }


/**/
    @Override
    public void create() {
       // client = new GameClient();

        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), true);
        plane = new Airplane("P51Mustang/spritesheet.atlas", world);
        plane2 = new Airplane("MitsubishiA6MZero/spritesheet.atlas", world);


        plane2.getPhysicsModel().getBody().setTransform(60f, 2f, 0.0f);
        plane2.update();

        Gdx.input.setInputProcessor(this);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

/****************************************************************************************************************/
        backgroundTexture = new Texture("back.png");
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundSprite = new Sprite(backgroundTexture);
/****************************************************************************************************************/


    }

    @Override
    public void render() {
        world.step(1f/60f, 6, 2);
        plane.update();
        plane2.update();

        if(plane.getPhysicsModel().positionUpdateLeft) {
            camera.position.set(camera.position.x - 1920, camera.position.y, camera.position.z);
            plane.getPhysicsModel().positionUpdateLeft = false;
        }

        if(plane.getPhysicsModel().positionUpdateRight) {
            camera.position.set(camera.position.x + 1920, camera.position.y, camera.position.z);
            plane.getPhysicsModel().positionUpdateRight = false;
        }

        Gdx.gl.glClearColor(0.6f, 0.85f, 0.918f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector3 target = new Vector3(plane.getGraphicModel().getSprite().getX(), plane.getGraphicModel().getSprite().getY(), camera.position.z);

        if(target.y < Gdx.graphics.getHeight()/2) {
            target.y = Gdx.graphics.getHeight()/2;
        }
        camera.position.lerp(target, 0.25f);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(ConfigGlobal.getInstance().PIXELS_TO_METERS, ConfigGlobal.getInstance().PIXELS_TO_METERS, 0);

        batch.begin();

        batch.draw(backgroundSprite.getTexture(),
                0, 0,
                5760, 1080,
                0, 1,
                3, 0
        );

        plane.getGraphicModel().draw(batch);
        plane2.getGraphicModel().draw(batch);
        batch.end();

       // debugRenderer.render(world, debugMatrix);
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.SPACE) {
            plane.getGraphicModel().needRotate = true;
        }

        if(keycode == Input.Keys.RIGHT) {
            plane.getPhysicsModel().speedInc = true;
        }

        if(keycode == Input.Keys.LEFT) {
            plane.getPhysicsModel().speedDec = true;
        }

        if(keycode == Input.Keys.UP) {
            plane.getPhysicsModel().angleInc = true;
        }

        if(keycode == Input.Keys.DOWN) {
            plane.getPhysicsModel().angleDec = true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT) {
            plane.getPhysicsModel().speedInc = false;
        }

        if(keycode == Input.Keys.LEFT) {
            plane.getPhysicsModel().speedDec = false;
        }

        if(keycode == Input.Keys.UP) {
            plane.getPhysicsModel().angleInc = false;
        }

        if(keycode == Input.Keys.DOWN) {
            plane.getPhysicsModel().angleDec = false;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}