package com.chris_ingram.unnamed_platformer.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/14/2017.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    private Label countdownLabel;
    private static Label scoreLabel;

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label heroLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(UnnamedPlatformer.V_WIDTH,UnnamedPlatformer.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();

        table.top();
        table.setFillParent(true);
        countdownLabel =  new Label(String.format("%03d", worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("Cave Exit",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("Level",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        heroLabel = new Label("Hero",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(heroLabel).expandX().padTop(3);
        table.add(worldLabel).expandX().padTop(3);
        table.add(timeLabel).expandX().padTop(3);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }
    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }
    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }
}
