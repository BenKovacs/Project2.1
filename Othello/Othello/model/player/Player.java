package model.player;

public interface Player {

    int TYPE_HUMAN = 1;
    int TYPE_BOT = 2;

    void play();
    int getPlayerType();
}