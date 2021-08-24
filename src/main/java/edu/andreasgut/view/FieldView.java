package edu.andreasgut.view;


import edu.andreasgut.game.Move;
import edu.andreasgut.game.Position;
import edu.andreasgut.sound.SOUNDEFFECT;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.robot.Robot;
import javafx.util.Duration;


public class FieldView extends AnchorPane {

    private ImageView imageView;
    private Image image;
    private ViewManager viewManager;
    private Image player1StoneImage, player2StoneImage, emptyField, forbiddenField, allowedField;
    private ImageCursor player1StoneCursor, player2StoneCursor, player2HandCursor, player1HandCursor,
            player2killCursor, player1killCursor;
    private GridPane fieldGridPane;
    private Position[][] translationArrayGraphicToRepresentation;
    private int[][] translationArrayRepresentationToIndex;
    private final int COMPREACTIONTIME = 500;



    public FieldView(ViewManager viewManager, STONECOLOR player1Color, STONECOLOR player2Color) {
        this.viewManager = viewManager;
        this.getStyleClass().add("fieldview");
        initializeTranslationArray();
        imageView = new ImageView();
        image = new Image("edu/andreasgut/images/Spielfeld.png",
                600, 600, true, true);
        imageView.setImage(image);

        fieldGridPane = new GridPane();
        fieldGridPane.setPadding(new Insets(3));
        fieldGridPane.setOnMouseExited (action ->{
            imageView.getScene().setCursor(Cursor.DEFAULT);
        });

        setupPlayerImagesAndCursors(player1Color, player2Color);
        setupFields();

        this.getChildren().addAll(imageView,fieldGridPane);

    }

    private void setupPlayerImagesAndCursors(STONECOLOR player1Color, STONECOLOR player2Color){
        player1StoneImage = new Image(player1Color.getPathStone(), 85, 85, true, true);
        player2StoneImage = new Image(player2Color.getPathStone(), 85, 85, true, true);
        emptyField = new Image("edu/andreasgut/images/FullyTransparent.png");
        allowedField = new Image("edu/andreasgut/images/GreenTransparent.png");
        forbiddenField = new Image("edu/andreasgut/images/FullyTransparent.png");

        player1StoneCursor = new ImageCursor(new Image(player1Color.getPathStone(), 85, 85, true, true),42,42);
        player2StoneCursor = new ImageCursor(new Image(player2Color.getPathStone(), 85, 85, true, true), 42,42);

        player1HandCursor = new ImageCursor(new Image(player1Color.getPathMoveCursor(), 85, 85, true, true));
        player2HandCursor = new ImageCursor(new Image(player2Color.getPathMoveCursor(), 85, 85, true, true));

        player1killCursor = new ImageCursor(new Image(player1Color.getPathKillCursor(), 85, 85, true, true));
        player2killCursor = new ImageCursor(new Image(player2Color.getPathKillCursor(), 85, 85, true, true));
    }

    private void setupFields(){

        fieldGridPane.setGridLinesVisible(false);
        for (int row = 0; row < 7; row++){
            fieldGridPane.addRow(row);
        }
        for (int column = 0; column < 7; column++){
            fieldGridPane.addRow(column);
        }

        //Füllt Felder mit emptyField wenn in Repräsentation vorhanden, sonst mit forbiddenField
        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 7; column++) {
                if (translationArrayGraphicToRepresentation[row][column].getRing()!=-1){
                    ImageView tempImageView = new ImageView(emptyField);
                    fieldGridPane.add(tempImageView,row,column);}
                else {ImageView tempImageView = new ImageView(forbiddenField);
                    fieldGridPane.add(tempImageView,row,column);}}}
    }


    public Position humanGraphicPut() {
        Object loopObject = new Object();
        setPutCursor();
        final Position position = new Position();
        final int[] ring = new int[1];
        final int[] field = new int[1];

        for (Node n : fieldGridPane.getChildren()){
            if(((ImageView) n).getImage().equals(emptyField)){
            n.setOnMouseClicked(click ->{
                position.setRing(translateToRing(n));
                position.setField(translateToField(n));

                System.out.println("Feld in Repräsentationsarray: " + ring[0] + "/" + field[0]);
                System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));

                switch (viewManager.getGame().getCurrentPlayerIndex()){
                    case 0:
                        ((ImageView)n).setImage(player1StoneImage);
                        break;
                    case 1:
                        ((ImageView)n).setImage(player2StoneImage);
                        break;
                }

                Platform.exitNestedEventLoop(loopObject, null);

            });}}

        Platform.enterNestedEventLoop(loopObject);
        clearAllFieldFunctions();
        viewManager.getSoundManager().playSoundEffect(SOUNDEFFECT.PUT_STONE);
        moveMouseposition(20, 20);
        return position;
    }

    public Position humanGraphicKill(){
        Object loopObject = new Object();
        setKillCursor();

        final Position position = new Position();

        for (Node n : fieldGridPane.getChildren()){
            if(((ImageView) n).getImage().equals(getEnemysStoneImage()) &&
                    viewManager.getGame().getBoard().checkKill(new Position(translateToRing(n),translateToField(n)),
                    viewManager.getGame().getOtherPlayerIndex())) {
            n.setOnMouseClicked(click ->{
                position.setRing(translateToRing(n));
                position.setField(translateToField(n));

                System.out.println("Stein wurde gesetzt.");
                System.out.println("Feld in Repräsentationsarray: " + position.getRing() + "/" + position.getField());
                System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));

                ((ImageView) n).setImage(emptyField);
                Platform.exitNestedEventLoop(loopObject, null);
            });}}
        Platform.enterNestedEventLoop(loopObject);
        clearAllFieldFunctions();
        viewManager.getSoundManager().playSoundEffect(SOUNDEFFECT.KILL_STONE);
        moveMouseposition(20, 20);

        return position;
    }

    public Move humanGraphicMove() {

        Move move = new Move();
        boolean releasedOnAnotherField = false;

        while (!releasedOnAnotherField){

            setMoveCursor();

            final ImageView clickedField = humanGraphicMoveTakeStep(move)[0];
            clearAllFieldFunctions();

            setPutCursor();

            releasedOnAnotherField = humanGraphicMoveReleaseStep(move, clickedField);
            clearAllFieldFunctions();}

        return move;
    }

    private ImageView[] humanGraphicMoveTakeStep(Move move){
        Object loopObject = new Object();
        final ImageView[] clickedField = new ImageView[1];

        for (Node n : fieldGridPane.getChildren()){
            if(((ImageView) n).getImage().equals(getOwnStoneImage())){
                n.setOnMouseClicked(click ->{
                    move.setFrom(new Position(translateToRing(n), translateToField(n)));

                    System.out.println("Stein wurde genommen.");
                    System.out.println("Feld in Repräsentationsarray: " + move.getFrom().getRing() + "/" + move.getFrom().getField());
                    System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));

                    ((ImageView) n).setImage(emptyField);
                    Platform.exitNestedEventLoop(loopObject, null);
                    clickedField[0] = (ImageView) click.getSource();
                });}}
        Platform.enterNestedEventLoop(loopObject);
        return clickedField;
    }

    private boolean humanGraphicMoveReleaseStep(Move move, ImageView clickedField){
        Object loopObject = new Object();
        final boolean[] releasedOnAnotherfield = {false};
        for (Node n : fieldGridPane.getChildren()){
            move.setTo(new Position(translateToRing(n), translateToField(n)));
            if(((ImageView) n).getImage().equals(emptyField) &&
                    (viewManager.getGame().getBoard().checkMove(move,
                            viewManager.getGame().getBoard().countPlayersStones(viewManager.getGame().getCurrentPlayerIndex()) == 3))){
                n.setOnMouseClicked(click ->{
                    move.setTo(new Position(translateToRing(n), translateToField(n)));

                    System.out.println("Stein wurde hingelegt.");
                    System.out.println("Feld in Repräsentationsarray: " + move.getTo().getRing() + "/" + move.getTo().getField());
                    System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));

                    ((ImageView) n).setImage(getOwnStoneImage());
                    releasedOnAnotherfield[0] = true;
                    Platform.exitNestedEventLoop(loopObject, null);
                });}
            if (n == clickedField){
                n.setOnMouseClicked(click ->{
                    ((ImageView) n).setImage(getOwnStoneImage());
                    releasedOnAnotherfield[0] = false;
                    Platform.exitNestedEventLoop(loopObject, null);
                });
            }
        }
        Platform.enterNestedEventLoop(loopObject);
        setMoveCursor();
        return releasedOnAnotherfield[0];
    }

    public void onlineGraphicPut(Position position){

    }

    public void computerGraphicPut(Position position){
        Object loopObject = new Object();
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COMPREACTIONTIME),
                put -> {((ImageView) fieldGridPane.getChildren().get(translateToIndex(position))).setImage(player2StoneImage);
                        viewManager.getSoundManager().playSoundEffect(SOUNDEFFECT.PUT_STONE);
                        Platform.exitNestedEventLoop(loopObject, null);}));
        timeline.play();
        Platform.enterNestedEventLoop(loopObject);

    }

    public void computerGraphicMove(Move move){
        Object loopObject = new Object();
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COMPREACTIONTIME*1.5),
                moveAction -> {((ImageView) fieldGridPane.getChildren().get(translateToIndex(move.getFrom()))).setImage(emptyField);
                    ((ImageView) fieldGridPane.getChildren().get(translateToIndex(move.getTo()))).setImage(player2StoneImage);
                    viewManager.getSoundManager().playSoundEffect(SOUNDEFFECT.PUT_STONE);
                    Platform.exitNestedEventLoop(loopObject, null);}));
        timeline.play();
        Platform.enterNestedEventLoop(loopObject);
    }

    public void computerGraphicKill(Position position){
        Object loopObject = new Object();
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COMPREACTIONTIME*1.5),
                kill -> {((ImageView) fieldGridPane.getChildren().get(translateToIndex(position))).setImage(emptyField);
                        viewManager.getSoundManager().playSoundEffect(SOUNDEFFECT.KILL_STONE);
                        Platform.exitNestedEventLoop(loopObject, null);}));
        timeline.play();
        Platform.enterNestedEventLoop(loopObject);
    }

    private void moveMouseposition(int dx, int dy){
        Robot robot = new Robot();
        robot.mouseMove(robot.getMouseX()+dx, robot.getMouseY()+dy);
    }

    private Image getEnemysStoneImage(){
        return viewManager.getGame().getCurrentPlayerIndex()==0 ? player2StoneImage : player1StoneImage;
    }

    private Image getOwnStoneImage(){
        return viewManager.getGame().getCurrentPlayerIndex()==0 ? player1StoneImage : player2StoneImage;
    }

    private void setPutCursor(){
        choosePutCursor();
        fieldGridPane.setOnMouseEntered(enter ->{
            choosePutCursor();
        });
    }

    private void choosePutCursor() {
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                imageView.getScene().setCursor(player1StoneCursor);
                break;
            case 1:
                imageView.getScene().setCursor(player2StoneCursor);
                break;}
    }



    private void setKillCursor(){
       chooseKillCursor();
        fieldGridPane.setOnMouseEntered(enter ->{
            chooseKillCursor();
        });
    }

    private void chooseKillCursor() {
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                imageView.getScene().setCursor(player1killCursor);
                break;
            case 1:
                imageView.getScene().setCursor(player2killCursor);
                break;}
    }

    private void setMoveCursor(){
        chooseMoveCursor();
        fieldGridPane.setOnMouseEntered(enter ->{
            chooseMoveCursor();
        });
    }

    private void chooseMoveCursor(){
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                imageView.getScene().setCursor(player1HandCursor);
                break;
            case 1:
                imageView.getScene().setCursor(player2HandCursor);
                break;}
    }

    private void clearAllFieldFunctions(){
        for (Node n : fieldGridPane.getChildren()){
            n.setOnMouseClicked(click -> {/* clear old function*/});}
    }

    private int translateToRing(Node node){
        return translationArrayGraphicToRepresentation[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)].getRing();
    }

    private int translateToField(Node node){
        return translationArrayGraphicToRepresentation[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)].getField();
    }

    private int translateToIndex(Position position){
        return translationArrayRepresentationToIndex[position.getRing()][position.getField()];
    }

    private void initializeTranslationArray(){
        translationArrayGraphicToRepresentation = new Position[7][7];

        //Koordinaten -1/-1 bedeuten, dass Feld in FieldArray nicht repräsentiert wird!
        //Führt bei Feldinitialisierung zu einem forbiddenField
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                translationArrayGraphicToRepresentation[i][j] = new Position(-1,-1);
            }
        }

        translationArrayGraphicToRepresentation[0][0] = new Position(0,0);
        translationArrayGraphicToRepresentation[0][3] = new Position(0,1);
        translationArrayGraphicToRepresentation[0][6] = new Position(0,2);
        translationArrayGraphicToRepresentation[3][6] = new Position(0,3);
        translationArrayGraphicToRepresentation[6][6] = new Position(0,4);
        translationArrayGraphicToRepresentation[6][3] = new Position(0,5);
        translationArrayGraphicToRepresentation[6][0] = new Position(0,6);
        translationArrayGraphicToRepresentation[3][0] = new Position(0,7);
        translationArrayGraphicToRepresentation[1][1] = new Position(1,0);
        translationArrayGraphicToRepresentation[1][3] = new Position(1,1);
        translationArrayGraphicToRepresentation[1][5] = new Position(1,2);
        translationArrayGraphicToRepresentation[3][5] = new Position(1,3);
        translationArrayGraphicToRepresentation[5][5] = new Position(1,4);
        translationArrayGraphicToRepresentation[5][3] = new Position(1,5);
        translationArrayGraphicToRepresentation[5][1] = new Position(1,6);
        translationArrayGraphicToRepresentation[3][1] = new Position(1,7);
        translationArrayGraphicToRepresentation[2][2] = new Position(2,0);
        translationArrayGraphicToRepresentation[2][3] = new Position(2,1);
        translationArrayGraphicToRepresentation[2][4] = new Position(2,2);
        translationArrayGraphicToRepresentation[3][4] = new Position(2,3);
        translationArrayGraphicToRepresentation[4][4] = new Position(2,4);
        translationArrayGraphicToRepresentation[4][3] = new Position(2,5);
        translationArrayGraphicToRepresentation[4][2] = new Position(2,6);
        translationArrayGraphicToRepresentation[3][2] = new Position(2,7);

        translationArrayRepresentationToIndex = new int[3][8];
        translationArrayRepresentationToIndex[0][0] = 0;
        translationArrayRepresentationToIndex[0][1] = 21;
        translationArrayRepresentationToIndex[0][2] = 42;
        translationArrayRepresentationToIndex[0][3] = 45;
        translationArrayRepresentationToIndex[0][4] = 48;
        translationArrayRepresentationToIndex[0][5] = 27;
        translationArrayRepresentationToIndex[0][6] = 6;
        translationArrayRepresentationToIndex[0][7] = 3;
        translationArrayRepresentationToIndex[1][0] = 8;
        translationArrayRepresentationToIndex[1][1] = 22;
        translationArrayRepresentationToIndex[1][2] = 36;
        translationArrayRepresentationToIndex[1][3] = 38;
        translationArrayRepresentationToIndex[1][4] = 40;
        translationArrayRepresentationToIndex[1][5] = 26;
        translationArrayRepresentationToIndex[1][6] = 12;
        translationArrayRepresentationToIndex[1][7] = 10;
        translationArrayRepresentationToIndex[2][0] = 16;
        translationArrayRepresentationToIndex[2][1] = 23;
        translationArrayRepresentationToIndex[2][2] = 30;
        translationArrayRepresentationToIndex[2][3] = 31;
        translationArrayRepresentationToIndex[2][4] = 32;
        translationArrayRepresentationToIndex[2][5] = 25;
        translationArrayRepresentationToIndex[2][6] = 18;
        translationArrayRepresentationToIndex[2][7] = 17;
    }
}