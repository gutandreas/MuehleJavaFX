package edu.andreasgut.view;


import edu.andreasgut.game.Move;
import edu.andreasgut.game.Position;
import edu.andreasgut.sound.SOUNDEFFECT;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.robot.Robot;


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



    public FieldView(ViewManager viewManager, STONECOLOR player1Color, STONECOLOR player2Color, boolean activateBoardFunctions) {
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
        setupFields(activateBoardFunctions);

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

    private void setupFields(boolean activateBoardFunctions){

        fieldGridPane.setGridLinesVisible(false);

        for (int row = 0; row < 7; row++) {
            fieldGridPane.addRow(row);
        }
        for (int column = 0; column < 7; column++) {
            fieldGridPane.addRow(column);
        }

        //Füllt Felder mit emptyField wenn in Repräsentation vorhanden, sonst mit forbiddenField
        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 7; column++) {
                if (translationArrayGraphicToRepresentation[row][column].getRing() != -1) {
                    ImageView tempImageView = new ImageView(emptyField);
                    fieldGridPane.add(tempImageView, row, column);
                } else {
                    ImageView tempImageView = new ImageView(forbiddenField);
                    fieldGridPane.add(tempImageView, row, column);
                }
            }
        }


        if (activateBoardFunctions){

            for (Node n : fieldGridPane.getChildren()) {
                if (((ImageView) n).getImage().equals(emptyField)) {
                    n.setOnMouseClicked(click -> {
                        Position position = new Position(translateToRing(n), translateToField(n));

                        System.out.println("Feld in Repräsentationsarray: " + position.getRing() + "/" + position.getField());
                        System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));
                        System.out.println("Test");

                        viewManager.getGame().nextStep(position);
                    });
                }
            }
        }
    }




    public void graphicPut(Position position, int playerIndex, int delay){
        Object loopObject = new Object();

        Image image;

        if (playerIndex == 0){
            image = player1StoneImage;
        }
        else {
            image = player2StoneImage;
        }

        ((ImageView) fieldGridPane.getChildren().get(translateToIndex(position))).setImage(image);
        viewManager.getSoundManager().playSoundEffect(SOUNDEFFECT.PUT_STONE);


    }

    public void graphicMove(Move move, int playerIndex){
        Object loopObject = new Object();

        Image image;

        if (playerIndex == 0){
            image = player1StoneImage;
        }
        else {
            image = player2StoneImage;
        }

        ((ImageView) fieldGridPane.getChildren().get(translateToIndex(move.getFrom()))).setImage(emptyField);
        ((ImageView) fieldGridPane.getChildren().get(translateToIndex(move.getTo()))).setImage(image);
        viewManager.getSoundManager().playSoundEffect(SOUNDEFFECT.PUT_STONE);



        /*Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COMPREACTIONTIME*1.5),
                moveAction -> {((ImageView) fieldGridPane.getChildren().get(translateToIndex(move.getFrom()))).setImage(emptyField);
                    ((ImageView) fieldGridPane.getChildren().get(translateToIndex(move.getTo()))).setImage(player2StoneImage);
                    viewManager.getSoundManager().playSoundEffect(SOUNDEFFECT.PUT_STONE);
                    Platform.exitNestedEventLoop(loopObject, null);}));
        timeline.play();
        Platform.enterNestedEventLoop(loopObject);*/
    }

    public void graphicTake(Position position){
        ((ImageView) fieldGridPane.getChildren().get(translateToIndex(position))).setImage(emptyField);
    }

    public void graphicKill(Position position){


        ((ImageView) fieldGridPane.getChildren().get(translateToIndex(position))).setImage(emptyField);
        viewManager.getSoundManager().playSoundEffect(SOUNDEFFECT.KILL_STONE);



    }

    private void moveMouseposition(int dx, int dy){
        Robot robot = new Robot();
        robot.mouseMove(robot.getMouseX()+dx, robot.getMouseY()+dy);
    }

    private Image getEnemysStoneImage(){
        return  viewManager.getGame().getCurrentPlayerIndex()==0 ? player2StoneImage : player1StoneImage;
    }

    private Image getOwnStoneImage(){
        return viewManager.getGame().getCurrentPlayerIndex()==0 ? player1StoneImage : player2StoneImage;
    }

    synchronized public void setPutCursor(){
        choosePutCursor();
        fieldGridPane.setOnMouseEntered(enter ->{
            choosePutCursor();
        });
        moveMouseposition(20,20);
    }

    synchronized private void choosePutCursor() {
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                imageView.getScene().setCursor(player1StoneCursor);
                break;
            case 1:
                imageView.getScene().setCursor(player2StoneCursor);
                break;}
    }



    synchronized public void setKillCursor(){
       chooseKillCursor();
        fieldGridPane.setOnMouseEntered(enter ->{
            chooseKillCursor();
        });
    }

    synchronized private void chooseKillCursor() {
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                imageView.getScene().setCursor(player1killCursor);
                break;
            case 1:
                imageView.getScene().setCursor(player2killCursor);
                break;}
    }

    synchronized public void setMoveCursor(){
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