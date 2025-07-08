/** Gaborni, Kyle Jon B.
 *  CCC121 IT2B Multimedia Laboratory
 *  Implement a Dijkstra algorithm with its relevant functions or methods
 *  Input: Assign distance values to all vertices in input graph
 *  Output: Displays graph and its output with nodes attached to edge costs, and vertices labeled inside nodes
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;
public class Gaborni_2021_1254_Dijkstra{
    public static void main(String[] args) {
        frameGUI frameGUI = new frameGUI();
    }
}
class frameGUI extends JFrame implements ActionListener {
    //These are all initialized components required for the Java Swing library to work. These are the indices needed for locating the different circles,
    //alongside the different buttons, panels, and the instance of the class used for the algorithm.
    int nodeCount = 0;
    int initialNodeIndex = 0;
    int finalNodeIndex = 0;
    int toggledNodeIndex = 0;
    int chosenCurrIndex = 0;
    int chosenDesIndex = 0;
    String[] nodeLabel = new String[0];
    JComboBox currNodeBox, desNodeBox, toggledNodeBox, initialNodeBox, finalNodeBox;
    dijkstraAlgo t = new dijkstraAlgo();
    JButton addButton, calcButton, resetButton, minimalButton;
    JPanel optionPanel, toolPanel, menuPanel, graphPanel;
    DragArea dragArea = new DragArea();
    Color graphColor = new Color(94, 109, 150);
    Color menuColor = new Color(31, 33, 42);
    frameGUI(){
        //This is the button for adding nodes, these lines of code simply adds the different properties needed for the button to look presentable and functioning.
        addButton = new JButton();
        addButton.setBounds(0,0,0,0);
        addButton.setText("ADD NODE");
        addButton.addActionListener(this);
        addButton.setFocusable(false);
        addButton.setHorizontalTextPosition(JButton.CENTER);
        addButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        addButton.setBackground(graphColor);

        //This is the button for calculating the shortest path. The buttons below will all have similar properties for setting their sizes, font, color and background
        calcButton = new JButton();
        calcButton.setBounds(0,0,0,0);
        calcButton.setText("CALCULATE");
        calcButton.addActionListener(this);
        calcButton.setFocusable(false);
        calcButton.setHorizontalTextPosition(JButton.CENTER);
        calcButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        calcButton.setBackground(graphColor);

        //This is the button for resetting nodes.
        resetButton = new JButton();
        resetButton.setBounds(0,0,0,0);
        resetButton.setText("RESET FRAME");
        resetButton.addActionListener(this);
        resetButton.setFocusable(false);
        resetButton.setHorizontalTextPosition(JButton.CENTER);
        resetButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        resetButton.setBackground(graphColor);

        //This is the button for finding the MCST of the dijkstra algorithm
        minimalButton = new JButton();
        minimalButton.setBounds(0,0,0,0);
        minimalButton.setText("MINIMAL COST");
        minimalButton.addActionListener(this);
        minimalButton.setFocusable(false);
        minimalButton.setHorizontalTextPosition(JButton.CENTER);
        minimalButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        minimalButton.setBackground(graphColor);

        //This is the label for the toggled node section in the option panel. This block of code also has the combo box showing all the nodes that drop down where you can pick a node.
        JLabel toggledNodeLabel = new JLabel("Toggle Node:");
        toggledNodeLabel.setForeground(Color.WHITE);
        toggledNodeLabel.setBounds(0,0,100,250/6);
        toggledNodeBox = new JComboBox(nodeLabel);
        toggledNodeBox.addActionListener(this);
        toggledNodeBox.setBounds(120,0,100,250/6);

        //Similar to the toggle node, you can choose what node you want as the current/"from" node. All the labels below also have its own combo box to choose the nodes.
        JLabel currNodeLabel = new JLabel("Current Node:");
        currNodeLabel.setForeground(Color.WHITE);
        currNodeLabel.setBounds(0,250/6,100,250/6);
        currNodeBox = new JComboBox(nodeLabel);
        currNodeBox.addActionListener(this);
        currNodeBox.setBounds(120,250/6,100,250/6);

        //This for the destination node label and its combo box where you can choose the destination node.
        JLabel desNodeLabel = new JLabel("Destination Node:");
        desNodeLabel.setForeground(Color.WHITE);
        desNodeLabel.setBounds(0,250/3,100,250/6);
        desNodeBox = new JComboBox(nodeLabel);
        desNodeBox.addActionListener(this);
        desNodeBox.setBounds(120,250/3,100,250/6);

        //This is the weight label alongside a text field where you can input only positive integers for the edge cost of the
        //lines based on the current and destination node you want to connect.
        JLabel weightLabel = new JLabel("Weight:");
        weightLabel.setForeground(Color.WHITE);
        weightLabel.setBounds(0,125,100,250/6);
        JTextField weightTextField = new JTextField();

        //This part of the code checks if the enter key is pressed, and if so, it enters the weight, given that the inputted number is a positive integer. It will give out an error message if the user inputs an invalid answer and asks to input again.
        weightTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputErrorTest(weightTextField.getText())){
                    JOptionPane.showMessageDialog(null, "Input only a positive integer", "Error Warning", JOptionPane.WARNING_MESSAGE);
                    weightTextField.setText("");
                    weightTextField.requestFocus();
                }
                if (chosenDesIndex != chosenCurrIndex) {
                    t.graph[chosenCurrIndex][chosenDesIndex] = Integer.parseInt(weightTextField.getText());
                    t.graph[chosenDesIndex][chosenCurrIndex] = t.graph[chosenCurrIndex][chosenDesIndex];
                    t.displayGraph[chosenDesIndex][chosenCurrIndex] = String.valueOf(t.graph[chosenCurrIndex][chosenDesIndex]);
                    t.displayGraph[chosenCurrIndex][chosenDesIndex] = t.displayGraph[chosenDesIndex][chosenCurrIndex];
                    repaint(); t.printArray();
                }else JOptionPane.showMessageDialog(null, "Connect to a different node other than itself.", "Error Warning", JOptionPane.WARNING_MESSAGE);
            }
        });weightTextField.setBounds(120,125,100,250/6);
        //This is the initial label and its combo box. It handles the initial node that will be used as the "source" in the algorithm.
        JLabel initialLabel = new JLabel("Initial:");
        initialLabel.setForeground(Color.WHITE);
        initialLabel.setBounds(0,500/3,100,250/6);
        initialNodeBox = new JComboBox(nodeLabel);
        initialNodeBox.setBounds(120, 500/3, 100, 250/6);
        initialNodeBox.addActionListener(this);

        //This is the final label and its combo box. It handles the final node that will be used as the "destination" in the algorithm.
        JLabel finalLabel = new JLabel("Final:");
        finalLabel.setForeground(Color.WHITE);
        finalLabel.setBounds(0,625/3,100,250/6);
        finalNodeBox = new JComboBox(nodeLabel);
        finalNodeBox.setBounds(120,625/3,100,250/6);
        finalNodeBox.addActionListener(this);

        //These are all the panels used to place the components into, just the required settings for each to display properly.
        menuPanel = new JPanel();
        menuPanel.setBackground(menuColor);
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        menuPanel.setPreferredSize(new Dimension(800,800/3));
        graphPanel = new JPanel();
        graphPanel.setBackground(graphColor);
        graphPanel.setPreferredSize(new Dimension(400, 1600/3));
        optionPanel = new JPanel();
        optionPanel.setBackground(menuColor);
        optionPanel.setPreferredSize(new Dimension(250,250));
        optionPanel.setLayout(null);
        toolPanel = new JPanel();
        toolPanel.setPreferredSize(new Dimension(250,250));
        toolPanel.setLayout(new GridLayout(3,1));
        toolPanel.setBackground(menuColor);

        //This block of code is just adding the components to another component until the major components are added to the frame.
        toolPanel.add(addButton);
        toolPanel.add(calcButton);
        toolPanel.add(resetButton);
        toolPanel.add(minimalButton);
        optionPanel.add(toggledNodeLabel);
        optionPanel.add(toggledNodeBox);
        optionPanel.add(currNodeLabel);
        optionPanel.add(currNodeBox);
        optionPanel.add(desNodeLabel);
        optionPanel.add(desNodeBox);
        optionPanel.add(weightLabel);
        optionPanel.add(weightTextField);
        optionPanel.add(initialLabel);
        optionPanel.add(finalLabel);
        optionPanel.add(initialNodeBox);
        optionPanel.add(finalNodeBox);
        menuPanel.add(optionPanel);
        menuPanel.add(toolPanel);

        //Layout for the major panels.
        this.setLayout(new BorderLayout());
        this.add(menuPanel, BorderLayout.SOUTH);
        this.add(graphPanel, BorderLayout.NORTH);
        graphPanel.add(dragArea);

        //Adding the class required for moving the nodes; standard code for initializing frame.
        this.setSize(900,750);
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle("Dijkstra's Algorithm Implementation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //These are all listeners used to detect if any of the buttons are interacted with/pressed.
    @Override
    public void actionPerformed(ActionEvent e) {
        //This is the listener for the add button, it simply does the addNode() method and repaints to make sure the circles are updated.
        if(e.getSource()==addButton) {
            addNode();
            repaint();
        }
        //This is for the calc button. It simply uses the algorithm with the initial node set previously as the source.
        if(e.getSource()==calcButton) {
            int result = t.dijkstra(initialNodeIndex);
            dragArea.setSPT(t.SPT);
            if (result == Integer.MAX_VALUE){
                JOptionPane.showMessageDialog(null, "The initial node and destination is not connected.", "Input Warning", JOptionPane.WARNING_MESSAGE);
            }else JOptionPane.showMessageDialog(null, "The shortest distance is: " + result, "Result", JOptionPane.PLAIN_MESSAGE);
        }
        //This is the reset button. It resets everything.
        if(e.getSource()==resetButton) {
            t.graph = new int[1][1];
            nodeCount = 0;
            nodeLabel = new String[0];
            dragArea.XNodeArr = new int[0];
            dragArea.YNodeArr = new int[0];
            finalNodeBox.removeAllItems();
            currNodeBox.removeAllItems();
            desNodeBox.removeAllItems();
            initialNodeBox.removeAllItems();
            toggledNodeBox.removeAllItems();
            repaint();
        }

        /*if (e.getSource() == minimalButton){ //this is the minimal button. It calculates the minimal cost spannig tree
            for (int i = 0; i < size(); i++){


            }
            repaint();
        }
        //*/

        //The following combo boxes simply fills all the required nodes' index values.
        //This the combo box for the current/source node.
        if (e.getSource() == currNodeBox){
            String chosenCurr = (String) currNodeBox.getSelectedItem();
            for (int i = 0; i < nodeCount; i++) {
                if (nodeLabel[i].equals(chosenCurr)){
                    chosenCurrIndex = i;
                }
            }
        }
        //This the combo box for the destination node.
        if (e.getSource() == desNodeBox){
            String chosenDes = (String) desNodeBox.getSelectedItem();
            for (int i = 0; i < nodeCount; i++) {
                if (nodeLabel[i].equals(chosenDes)){
                    chosenDesIndex = i;
                }
            }
        }
        //This the combo box for the toggled node.
        if (e.getSource() == toggledNodeBox){
            String toggledNode = (String) toggledNodeBox.getSelectedItem();
            for (int i = 0; i < nodeCount; i++) {
                if (nodeLabel[i].equals(toggledNode)){
                    toggledNodeIndex = i;
                }
            }
        }
        //This the combo box for the initial node.
        if (e.getSource() == initialNodeBox){
            String initialNode = (String) initialNodeBox.getSelectedItem();
            for (int i = 0; i < nodeCount; i++) {
                if (nodeLabel[i].equals(initialNode)){
                    initialNodeIndex = i;
                }
            }
        }
        //This the combo box for the final node.
        if (e.getSource() == finalNodeBox){
            String finalNode = (String) finalNodeBox.getSelectedItem();
            for (int i = 0; i < nodeCount; i++) {
                if (nodeLabel[i].equals(finalNode)){
                    finalNodeIndex = i;
                }
            }
        }
    }
    public static boolean inputErrorTest(String input){ //This function tests whether a number is negative or is not an integer.
        if(isNumeric(input)){
            return !input.contains("-") && !input.contains(".");
        }
        else return false;
    }

    //This function checks whether the value is a digit in between 0 and 9.
    private static boolean isNumeric(String str){return str != null && str.matches("[0-9.]+");}
    void addNode(){//This block of code simply adds all the nodes, into the combo boxes for the user to choose from. It also
        //places nodes in an array 'nodeLabel' to be used as the container for the names of the nodes. It also adds one to the node count.
        t.addNode();
        dragArea.addNode();
        currNodeBox.addItem("Node" + nodeCount);
        desNodeBox.addItem("Node" + nodeCount);
        toggledNodeBox.addItem("Node" + nodeCount);
        initialNodeBox.addItem("Node" + nodeCount);
        finalNodeBox.addItem("Node" + nodeCount);
        if (nodeLabel.length == nodeCount){
            String[] newNodeLabel = new String[nodeCount + 1];
            for (int i = 0; i < nodeCount; i++) {
                newNodeLabel[i] = nodeLabel[i];
            }
            nodeLabel = newNodeLabel;
        }
        nodeLabel[nodeCount] = "Node" + nodeCount;
        nodeCount++;
        t.printArray();
    }
    class DragArea extends JPanel{
        //The DragArea class is a JPanel that allows the user to drag points displayed on the panel using the mouse.
        // It has two instance variables, XNodeArr and YNodeArr, which store the x and y coordinates of the points.
        // It also has a nested class called MouseDrag which handles mouse events (press, release, and drag) and updates the values in the
        // XNodeArr and YNodeArr arrays as the user drags the points.
        int[][] SPT;
        boolean SPT_isSet;
        int[] XNodeArr = new int[1];
        int[] YNodeArr = new int[1];
        private final MouseDrag mouseDrag;
        private final class MouseDrag extends MouseAdapter {
            private boolean dragging = false;
            private Point last;
            @Override
            public void mousePressed(MouseEvent m) {
                last = m.getPoint();
                dragging = isInsideEllipse(last);
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent m) {
                last = null;
                dragging = false;
                repaint();
            }
            @Override
            public void mouseDragged(MouseEvent m) {
                int dx = m.getX() - last.x;
                int dy = m.getY() - last.y;
                if (dragging) {
                    XNodeArr[toggledNodeIndex] += dx;
                    YNodeArr[toggledNodeIndex] += dy;
                }
                last = m.getPoint();
                repaint();
            }
        }
        //This is the constructor for the drag area panel that adds all the required components for the mouse to interact with the circles.
        DragArea(){
            this.setBackground(graphColor);
            this.setPreferredSize(new Dimension(800, 500));
            mouseDrag = new MouseDrag();
            addMouseListener(mouseDrag);
            addMouseMotionListener(mouseDrag);
            SPT_isSet = false;
        }

        public void setSPT(int[][] spt){
            SPT = spt;
            SPT_isSet = true;
            repaint();
        }
        //This checks if the mouse is on the ellipse to be used as a property to determine if the user is dragging the circles.
        public boolean isInsideEllipse(Point point) {
            return new Ellipse2D.Float(XNodeArr[toggledNodeIndex], YNodeArr[toggledNodeIndex], 45, 45).contains(point);
        }//This is the paint component that the panel already has, that is being overridden with draw functions for the circles and lines.
        public void paintComponent(Graphics g){
            int totalCost = 0;
            super.paintComponent(g);
            //This is for the lines that are drawn before the circles that use the previous arrays.
            for (int i = 0; i < nodeCount; i++) {
                for (int j = i; j < nodeCount; j++) {
                    if (t.graph[i][j] != 0){
                        g.setColor(Color.white);
                        if(SPT_isSet && SPT[i][j] != Integer.MAX_VALUE){
                            g.setColor(Color.green);
                            totalCost = totalCost + SPT[i][j];
                        }
                        g.drawLine(XNodeArr[i]+12, YNodeArr[i]+30, XNodeArr[j]+12, YNodeArr[j]+30);
                        g.setColor(Color.green);
                        Font font = new Font("Verdana", Font.BOLD, 16);
                        g.setFont(font);
                        String weight = "" + t.graph[i][j];
                        int midPoint1X = (XNodeArr[j] + XNodeArr[i])/2;
                        int midPoint2X = (Math.abs(XNodeArr[j] - XNodeArr[i])/2)/2;
                        int y = (YNodeArr[j]+25 + YNodeArr[i]+25)/2;
                        int x = midPoint1X + midPoint2X;
                        g.drawString(weight, x, y);
                    }
                }
            }
            //This is for the circles, it has the node label drawn over the circle.
            for (int i = 0; i < nodeCount; i++) {
                g.setColor(new Color(215, 128, 21));
                g.fillOval(XNodeArr[i]-13, YNodeArr[i]+10, 45, 45);
                Font font = new Font("Verdana", Font.PLAIN, 12);
                g.setFont(font);
                g.setColor(new Color(108, 49, 0));
                String text = nodeLabel[i];
                g.drawString(text, XNodeArr[i]-10, YNodeArr[i]+36);
            }
            g.setColor(Color.green);
            Font font = new Font("Verdana", Font.PLAIN, 12);
            g.setFont(font);
            g.drawString("Shortest Path Tree Total Cost: " + ((SPT_isSet)? totalCost : "N/A"), 20, 20);
        }
        //This block of code simply adds an element in the x and y position arrays that are used for the position
        //of the circles in the graphPanel, it sets a random integer for the x and y coordinates of the circles.
        void addNode(){
            Random r = new Random();
            if (XNodeArr.length == nodeCount){
                int[] newXNodeArr = new int[nodeCount+1];
                int[] newYNodeArr = new int[nodeCount+1];
                for (int i = 0; i < nodeCount; i++) {
                    newYNodeArr[i] = YNodeArr[i];
                    newXNodeArr[i] = XNodeArr[i];
                }
                XNodeArr = newXNodeArr;
                YNodeArr = newYNodeArr;
            }
            int tempNodeCount = nodeCount;
            XNodeArr[tempNodeCount] = r.nextInt(400)+200;
            YNodeArr[tempNodeCount] = r.nextInt(400);
        }
    }
    class dijkstraAlgo{
        Boolean firstPrint = true;
        private int[][] graph = new int[1][1];//This line declares a private 2D array called graph and initializes it with a single element, which is set to 0.
        private int[][] SPT;
        private String[][] displayGraph = new String[1][1];
        public void addNode(){
            //This block of code copies the elements of the graph array into the newArr array.
            if (graph.length == nodeCount) {
                int[][] newArr = new int[2 * nodeCount][2 * nodeCount];
                String[][] newDisplayArr = new String[2 * nodeCount][2 * nodeCount];
                for (int i = 0; i < 2*nodeCount; i++) {
                    for (int j = 0; j < 2*nodeCount; j++) {
                        if (i==j){
                            newDisplayArr[i][j] = "0";
                        }else newDisplayArr[i][j] = "—";
                    }
                }
                for (int i = 0; i < nodeCount; i++) {
                    for (int j = 0; j < nodeCount; j++) {
                        newArr[i][j] = graph[i][j];
                        if (graph[i][j] != 0){
                            newDisplayArr[i][j] = String.valueOf(graph[i][j]);
                        }
                    }
                }
                graph = newArr;
                displayGraph = newDisplayArr;
            }
        }
        public void printArray(){ //This block of code simply prints the current state of the adjacency matrix
            // Iterating over array using for loop
            System.out.println("Current Adjacency Matrix:");
            for(int i = 0 ; i < nodeCount ; i++){
                for(int j = 0 ; j < nodeCount; j++)
                {
                    if (firstPrint){
                        System.out.print("0");
                        firstPrint = false;
                    }else System.out.print(displayGraph[i][j] + "\t" );
                }
                System.out.println();
            }
        }
        //This block declares a class called ShortestPath and a method called addNode within it.
        // The addNode method checks if the length of the graph array is equal to a variable called nodeCount.
        // If it is, it declares a new 2D array called newArr with dimensions 2 * nodeCount by 2 * nodeCount and copies
        // the elements of the graph array into it. The graph array is then set to the value of newArr.
        int minDist(int[] dist, Boolean[] exploredSet){
            int minIndex = -1;
            int min = Integer.MAX_VALUE;
            for (int v = 0; v < nodeCount; v++)
                if (dist[v] <= min && !exploredSet[v]){
                    min = dist[v];
                    minIndex = v;
                }
            return minIndex;
        }
        //This block declares a method called minDistance that takes two arguments: an array of integers called dist and an array of
        // booleans called exploredSet. The method iterates over the exploredSet array and finds the minimum value in the dist
        // array that has not been marked as true in the exploredSet array. The minimum value and its index are returned.
        int dijkstra(int src){
            SPT = new int[nodeCount][nodeCount];
            for(int r = 0; r < nodeCount; r++){
                for(int c = 0; c < nodeCount; c++){
                    SPT[r][c] = Integer.MAX_VALUE;
                }
            }
            int[] endEdgeSources = new int[nodeCount];

            Boolean[] exploredSet = new Boolean[nodeCount];
            int[] dist = new int[nodeCount];
            for (int i = 0; i < nodeCount; i++) {
                dist[i] = Integer.MAX_VALUE;
                exploredSet[i] = false;
                endEdgeSources[i] = -1;
            }
            dist[src] = 0;

            endEdgeSources[src] = src;
            //This block declares a method called dijkstra that takes a single integer argument called src.
            // It declares an array of integers called dist with length nodeCount and an array of booleans called exploredSet with length nodeCount.
            // It then initializes all elements of both arrays to their default values: dist elements are set to the maximum value
            // of an integer and exploredSet elements are set to false. The element at index src in the dist array is set to 0.
            for (int count = 0; count < nodeCount - 1; count++) {
                int u = minDist(dist, exploredSet);
                exploredSet[u] = true;
                for (int v = 0; v < nodeCount; v++){
                    if (!exploredSet[v] && graph[u][v] != 0 && dist[u] + graph[u][v] < dist[v] && dist[u] != Integer.MAX_VALUE) {
                        endEdgeSources[v] = u;
                        dist[v] = dist[u] + graph[u][v];
                    }
                }
            }
            //This block of code is a for loop that iterates nodeCount - 1 times. In each iteration, it calls the minDistance method to find the minimum distance
            // from the source node and marks it as true in the exploredSet array. It then checks all the nodes
            // that are not marked as true in the exploredSet array and updates their distance values in the dist array if the new distance is shorter.
            // Finally, it returns the value of the shortest path that ends with the final node set by the user.

            for(int u = 0; u < nodeCount; u++){
                for(int v = 0; v < nodeCount; v++){
                    if(u == v){
                        SPT[u][v] = 0;
                    }else if(v == endEdgeSources[u] && SPT[u][v] == Integer.MAX_VALUE){
                        SPT[u][v] = graph[u][v];
                        SPT[v][u] = graph[v][u];
                    }
                }
            }
            System.out.println("Shortest Path Tree:");
            for(int r = 0; r < nodeCount; r++){
                for(int c = 0; c < nodeCount; c++){
                    System.out.print(((SPT[r][c] == Integer.MAX_VALUE)? "—" : SPT[r][c]) + "\t");
                }
                System.out.println();
            }
            return dist[finalNodeIndex];
        }
    }
}