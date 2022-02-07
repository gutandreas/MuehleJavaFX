package edu.andreasgut.view;


import edu.andreasgut.game.Move;
import edu.andreasgut.game.Position;
import edu.andreasgut.sound.SOUNDEFFECT;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

abstract public class BoardView extends AnchorPane{

    protected ImageView imageView;
    private Image image;
    protected ViewManager viewManager;
    protected STONECOLOR player1Color, player2Color;
    private Image player1StoneImage;
    private Image player2StoneImage;
    protected Image emptyField;
    protected Image forbiddenField;

    protected GridPane fieldGridPane;
    protected Position[][] GUICoordinatesToPositionArray;
    private int[][] PositionToGridPaneIndexArray;
    private boolean boardFunctionsActive;



    public BoardView(ViewManager viewManager, STONECOLOR player1Color, STONECOLOR player2Color, boolean boardFunctionsActive) {
        this.viewManager = viewManager;
        this.getStyleClass().add("boardview");
        this.boardFunctionsActive = boardFunctionsActive;
        this.player1Color = player1Color;
        this.player2Color = player2Color;


        imageView = new ImageView();
        image = new Image("edu/andreasgut/images/Spielfeld.png", 600, 600, true, true);
        imageView.setImage(image);

        setupPlayerImages(player1Color, player2Color);

        initializeTranslationArrays();
        fieldGridPane = new GridPane();
        setupGridPane();
        fieldGridPane.setPadding(new Insets(3));
        fieldGridPane.setOnMouseExited (action ->{
            imageView.getScene().setCursor(Cursor.DEFAULT);
        });

        this.getChildren().addAll(imageView,fieldGridPane);
    }

    private void setupPlayerImages(STONECOLOR player1Color, STONECOLOR player2Color){
        player1StoneImage = new Image(player1Color.getPathStone(), 85, 85, true, true);
        player2StoneImage = new Image(player2Color.getPathStone(), 85, 85, true, true);
        emptyField = new Image("edu/andreasgut/images/FullyTransparent.png");
        forbiddenField = new Image("edu/andreasgut/images/FullyTransparent.png");
    }

    private void setupGridPane(){
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
                if (GUICoordinatesToPositionArray[row][column] != null) {
                    fieldGridPane.add(new ImageView(emptyField), row, column);
                } else {
                    fieldGridPane.add(new ImageView(forbiddenField), row, column);
                }
            }
        }
    }



    public void graphicPut(Position position, int playerIndex, boolean sound){

        Image image;

        if (playerIndex == 0){
            image = player1StoneImage;
        }
        else {
            image = player2StoneImage;
        }

        Platform.runLater(() -> {
                ((ImageView) fieldGridPane.getChildren().get(translateToIndex(position))).setImage(image);
                if (sound){
                    viewManager.getAudioPlayer().playSoundEffect(SOUNDEFFECT.PUT_STONE);}
        });
    }

    public void graphicMove(Move move, int playerIndex){

        Image image;

        if (playerIndex == 0){
            image = player1StoneImage;
        }
        else {
            image = player2StoneImage;
        }

        Platform.runLater(() -> {
            ((ImageView) fieldGridPane.getChildren().get(translateToIndex(move.getFrom()))).setImage(emptyField);
            ((ImageView) fieldGridPane.getChildren().get(translateToIndex(move.getTo()))).setImage(image);
            viewManager.getAudioPlayer().playSoundEffect(SOUNDEFFECT.PUT_STONE);
        });

    }

    public void graphicRemove(Position position){
        ((ImageView) fieldGridPane.getChildren().get(translateToIndex(position))).setImage(emptyField);
    }

    public void graphicKill(Position position, boolean sound){

        Platform.runLater(() -> {
            ((ImageView) fieldGridPane.getChildren().get(translateToIndex(position))).setImage(emptyField);
            if (sound){
                viewManager.getAudioPlayer().playSoundEffect(SOUNDEFFECT.KILL_STONE);}
        });
    }

    private Image getEnemysStoneImage(){
        return  viewManager.getGame().getCurrentPlayerIndex()==0 ? player2StoneImage : player1StoneImage;
    }

    private Image getOwnStoneImage(){
        return viewManager.getGame().getCurrentPlayerIndex()==0 ? player1StoneImage : player2StoneImage;
    }

    private void clearAllFieldFunctions(){
        for (Node n : fieldGridPane.getChildren()){
            n.setOnMouseClicked(click -> {/* clear old function*/});}
    }

    public boolean areBoardFunctionsActive() {
        return boardFunctionsActive;
    }

    protected int translateToPositionRing(Node node){
        return GUICoordinatesToPositionArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)].getRing();
    }

    protected int translateToPositionField(Node node){
        return GUICoordinatesToPositionArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)].getField();
    }

    private int translateToIndex(Position position){
        return PositionToGridPaneIndexArray[position.getRing()][position.getField()];
    }

    protected void initializeTranslationArrays(){
        GUICoordinatesToPositionArray = new Position[7][7];

        GUICoordinatesToPositionArray[0][0] = new Position(0,0);
        GUICoordinatesToPositionArray[0][3] = new Position(0,1);
        GUICoordinatesToPositionArray[0][6] = new Position(0,2);
        GUICoordinatesToPositionArray[3][6] = new Position(0,3);
        GUICoordinatesToPositionArray[6][6] = new Position(0,4);
        GUICoordinatesToPositionArray[6][3] = new Position(0,5);
        GUICoordinatesToPositionArray[6][0] = new Position(0,6);
        GUICoordinatesToPositionArray[3][0] = new Position(0,7);
        GUICoordinatesToPositionArray[1][1] = new Position(1,0);
        GUICoordinatesToPositionArray[1][3] = new Position(1,1);
        GUICoordinatesToPositionArray[1][5] = new Position(1,2);
        GUICoordinatesToPositionArray[3][5] = new Position(1,3);
        GUICoordinatesToPositionArray[5][5] = new Position(1,4);
        GUICoordinatesToPositionArray[5][3] = new Position(1,5);
        GUICoordinatesToPositionArray[5][1] = new Position(1,6);
        GUICoordinatesToPositionArray[3][1] = new Position(1,7);
        GUICoordinatesToPositionArray[2][2] = new Position(2,0);
        GUICoordinatesToPositionArray[2][3] = new Position(2,1);
        GUICoordinatesToPositionArray[2][4] = new Position(2,2);
        GUICoordinatesToPositionArray[3][4] = new Position(2,3);
        GUICoordinatesToPositionArray[4][4] = new Position(2,4);
        GUICoordinatesToPositionArray[4][3] = new Position(2,5);
        GUICoordinatesToPositionArray[4][2] = new Position(2,6);
        GUICoordinatesToPositionArray[3][2] = new Position(2,7);

        PositionToGridPaneIndexArray = new int[3][8];

        PositionToGridPaneIndexArray[0][0] = 0;
        PositionToGridPaneIndexArray[0][1] = 21;
        PositionToGridPaneIndexArray[0][2] = 42;
        PositionToGridPaneIndexArray[0][3] = 45;
        PositionToGridPaneIndexArray[0][4] = 48;
        PositionToGridPaneIndexArray[0][5] = 27;
        PositionToGridPaneIndexArray[0][6] = 6;
        PositionToGridPaneIndexArray[0][7] = 3;
        PositionToGridPaneIndexArray[1][0] = 8;
        PositionToGridPaneIndexArray[1][1] = 22;
        PositionToGridPaneIndexArray[1][2] = 36;
        PositionToGridPaneIndexArray[1][3] = 38;
        PositionToGridPaneIndexArray[1][4] = 40;
        PositionToGridPaneIndexArray[1][5] = 26;
        PositionToGridPaneIndexArray[1][6] = 12;
        PositionToGridPaneIndexArray[1][7] = 10;
        PositionToGridPaneIndexArray[2][0] = 16;
        PositionToGridPaneIndexArray[2][1] = 23;
        PositionToGridPaneIndexArray[2][2] = 30;
        PositionToGridPaneIndexArray[2][3] = 31;
        PositionToGridPaneIndexArray[2][4] = 32;
        PositionToGridPaneIndexArray[2][5] = 25;
        PositionToGridPaneIndexArray[2][6] = 18;
        PositionToGridPaneIndexArray[2][7] = 17;
    }

    public void setStoneColors(STONECOLOR player1Color){
        if (player1Color == STONECOLOR.BLACK){
            this.player1Color = STONECOLOR.BLACK;
            player1StoneImage = new Image(this.player1Color.getPathStone(), 85, 85, true, true);
            player2Color = STONECOLOR.WHITE;
            player2StoneImage = new Image(player2Color.getPathStone(), 85, 85, true, true);
        }
        else {
            this.player1Color = STONECOLOR.WHITE;
            player1StoneImage = new Image(this.player1Color.getPathStone(), 85, 85, true, true);
            player2Color = STONECOLOR.BLACK;
            player2StoneImage = new Image(player2Color.getPathStone(), 85, 85, true, true);
        }
    }
}