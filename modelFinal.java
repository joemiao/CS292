package gizmoSampleCode.SampleCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.awt.Polygon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.Timer;

/**
 * This is a sample Swing GUI application.
 */
public class modelFinal {
	//This is a master map that contains all the gizmos once read in from txt file
	private static HashMap<String, String> boardGizmos = new HashMap<String, String>();
	//This keeps track of all the boundaries of the gizmos where the ball should change direction
	private static boolean[] bound = new boolean[640000];
	//This is just used to initialize graphics and variables just once
	private static boolean boardMade = false;
	
    public static void main(String[] args) {
    	//This fill the the boundary array to be true before the gizmos are read in and thus some bound[x] becomes false
    	for(int i =0;i<640000;i++){
    		bound[i]=true;
    	}
    	ApplicationWindow frame = new ApplicationWindow();
        // the following code realizes the top level application window
        frame.pack();
        frame.setVisible(true);
        boardGizmos = gizmoBoardReader(); //This reads the gizmos into the map        
    }

    /**
     * Overview: A BouncingBall is a mutable data type. It simulates a rubber 
     * ball bouncing inside a two dimensional box. It also provides methods 
     * that are useful for creating animations of the ball as it moves.
     */
    private static class BouncingBall {

        private final static double VELOCITY_STEP = 2.0;
        
        private int x = (int) ((Math.random() * 100.0) + 100.0);

        private int y = (int) ((Math.random() * 100.0) + 100.0);

        private int vx = (int) ((Math.random() * VELOCITY_STEP) + VELOCITY_STEP);

        private int vy = (int) ((Math.random() * VELOCITY_STEP) + VELOCITY_STEP);

        private int radius = 6;

        private Color color = new Color(255, 0, 0);
        

        // Keep track of the animation window that will be drawing this ball.
        private AnimationWindow win;

        /**
         * Constructor.
         * @param win Animation window that will be drawing this ball.
         */
        public BouncingBall(AnimationWindow win) {
            this.win = win;
        }

        /**
         * @modifies this
         * @effects Moves the ball according to its velocity.  Reflections off 
         * walls cause the ball to change direction.
         */
        public void move() {

            x += vx;
            //This loops checks to see if the ball has hit a bounding box of a gizmo in a certain direction
            for(int i = 0; i<2*radius ;i++){
            	if(bound[x-radius+i+(y-radius)*800]==false){
            		vy=-vy;
            	} if(bound[x-radius+i+(y+radius)*800]==false){
            		vy=-vy;
            	}
            }
            if (x <= radius) {
                x = radius;
                vx = -vx;
            }
            if (x >= win.getWidth() - radius) {
                x = win.getWidth() - radius;
                vx = -vx;
            }
            y += vy;
            if (y <= radius) {
                y = radius;
                vy = -vy;
            }
            if (y >= win.getHeight() - radius) {
                y = win.getHeight() - radius;
                vy = -vy;
            }
            //System.out.println("checking");
          //This loops checks to see if the ball has hit a bounding box of a gizmo in a certain direction
           for(int i = 0; i<radius ;i++){
            	if(bound[x-radius+(y-radius+i*2)*800]==false){
            		vy=-vy;
            	}
            	 if(bound[x-radius+(y+radius+i*2)*800]==false){
            		vy=-vy;
            	}
            }
        }

        /**
         * @modifies this
         * @effects Changes the velocity of the ball by a random amount
         */
        public void randomBump() {
            vx += (int) ((Math.random() * VELOCITY_STEP) - (VELOCITY_STEP/2));
            vx = -vx;
            vy += (int) ((Math.random() * VELOCITY_STEP) - (VELOCITY_STEP/2));
            vy = -vy;
        }

        /**
         * @modifies the Graphics object <g>.
         * @effects paints a circle on <g> reflecting the current position 
         * of the ball.
         * @param g Graphics context to be used for drawing.
         */
        public void paint(Graphics g) {

            // the "clip rectangle" is the area of the screen that needs to be
            // modified
            Rectangle clipRect = g.getClipBounds();
            // For this tiny program, testing whether we need to redraw is
            // kind of silly.  But when there are lots of objects all over the
            // screen this is a very important performance optimization
            if (clipRect.intersects(this.boundingBox())) {
                g.setColor(color);
                g.fillOval(x - radius, y - radius, radius + radius, radius
                                + radius);
            }
            
          
        }

        /**
         * @return the smallest rectangle that completely covers the current 
         * position of the ball.
         */
        public Rectangle boundingBox() {

            // a Rectangle is the x,y for the upper left corner and then the
            // width and height
            return new Rectangle(x - radius - 1, y - radius - 1, radius + radius + 2,
                            radius + radius + 2);
        }
    }

    // Note the very indirect way control flow works during an animation:
    //
    // (1) We set up an eventListener with a reference to the animationWindow.
    // (2) We set up a timer with a reference to the eventListener.
    // (3) We call timer.start().
    // (4) Every 20 milliseconds the timer calls eventListener.actionPerformed()
    // (5) eventListener.actionPerformed() modifies the logical
    //     datastructure (e.g. changes the coordinates of the ball).
    // (6) eventListener.actionPerformed() calls myWindow.repaint.
    // (7) Swing schedules, at some point in the future, a call to 
    //      myWindow.paint()
    // (8) myWindow.paint() tells various objects to paint
    //     themselves on the provided Graphics context.
    //
    // This may seem very complicated, but it makes the coordination of
    // all the various different kinds of user input much easier.  For
    // example here is how control flow works when the user presses the
    // mouse button:
    //
    // (1) We set up an eventListener (actually we just use the same
    //     eventListener that is being used by the timer.)
    // (2) We register the eventListener with the window using the
    //     addMouseListener() method.
    // (3) Every time the mouse button is pressed inside the window the
    //     window calls eventListener.mouseClicked().
    // (4) eventListener.mouseClicked() modifies the logical
    //     datastructures.  (In this example it calls ball.randomBump(), but
    //     in other programs it might do something else, including request a
    //     repaint operation).
    //

    /**
     * Overview: an AnimationWindow is an area on the screen in which a 
     * bouncing ball animation occurs.  AnimationWindows have two modes:
     * on and off.  During the on mode the ball moves, during the off
     *  mode the ball doesn't move.
     */

    private static class AnimationWindow extends JComponent {

        private static final long serialVersionUID = 3257281448464364082L;

        // Controls how often we redraw
        private static int FRAMES_PER_SECOND = 100;

        private AnimationEventListener eventListener;

        private BouncingBall ball;

        private Timer timer;

        private boolean mode;

        /**
         * @effects initializes this to be in the off mode.
         */
        public AnimationWindow() {
            super(); // do the standard JPanel setup stuff
            ball = new BouncingBall(this);
            // this only initializes the timer, we actually start and stop the
            // timer in the setMode() method
            eventListener = new AnimationEventListener();
            // The first parameter is how often (in milliseconds) the timer
            // should call us back. 
            timer = new Timer(1000 / FRAMES_PER_SECOND, eventListener);
            mode = false;
        }       
        /**
         * @modifies g
         * @effects Repaints the Graphics area g.  Swing will then send the newly painted g to the screen.
         * @param g Graphics context received by either system or app calling repaint()
         */
        @Override public void paintComponent(Graphics g) {
            // first repaint the proper background color (controlled by
            // the windowing system)
            super.paintComponent(g);
            //this.setBackground(Color.white);
            ball.paint(g);
            //Makes the board only once, I"m not sure if this is needed
            //reads in the map from the text file
            HashMap<String, String> map  = gizmoBoardReader();
            //goes though the whole map
            for (String k: map.keySet()){
                String key =k;  
                String value = map.get(k);  
                //System.out.println(key + " " + value);  
                String[] parts = value.split(" ");
            	//this splits every key value into an array such that
            	//parts[0]=Shape parts[1]=x coord parts [2]=y coord
            	g.setColor(Color.WHITE);     	
            	Integer x =Integer.valueOf(parts[1]); //X coordinate variable for individual gizmo
            	Integer y =Integer.valueOf(parts[2]);  // Y coordinate variable for individual gizmo
        		int xarray[] = new int[3];//This is use to draw triangle x coordinates
        		int yarray[] = new int[3];//This is use to draw triangle y coordinates
        		//This if else chain then draws each gizmo and input the boundary points of each gizmo into bound[]
            	if(parts[0].equals("Triangle")){
            		xarray[0] = x; 
            		xarray[1] = x;
            		xarray[2] = x + 50;
            		yarray[0] = y; 
            		yarray[1] = y + 50;
            		yarray[2] = y + 50;
            		g.setColor(Color.YELLOW);
            		Polygon p = new Polygon(xarray, yarray, 3);  // We then draw a triangle by drawing a 3 point polygon
            		collision("Triangle",x,y,50,50);
            		g.fillPolygon(p);   
		        }else if(parts[0].equals("Square")){
		        	g.setColor(Color.BLACK);
		        	g.fillRect(x, y, 20, 20);
		        	if(boardMade==false){
		        		collision("Square",x,y,20,20);
		        	}
		        }else if(parts[0].equals("Circle")){
		        	g.setColor(Color.WHITE);
		        	g.fillOval(x, y, 20, 20);
		        	if(boardMade==false){
		        		collision("Circle",x-5,y-5,26,26);
		        	}
		        }else if(parts[0].equals("Bumper")){
		        	g.setColor(Color.BLACK);
		        	g.fillRect(x, y, 60, 20);
		        	if(boardMade==false){
		        		collision("Bumper",x,y,20,20);
		        	}
		        }else if(parts[0].equals("LeftFlipper")){
		        	g.setColor(Color.BLUE);
		        	g.fillRect(x, y, 15, 25);
		        }else if(parts[0].equals("RightFlipper")){
		        	g.setColor(Color.BLUE);
		        	g.fillRect(x, y, 15, 25);
		        }else if(parts[0].equals("Absorber")){
		        	g.setColor(Color.GREEN);
		        	g.fillRect(x, y, 25, 60);
		        }else if(parts[0].equals("KeyConnect")){
		        	//do nothing for now
		        }else if(parts[0].equals("Connect")){
		        	//do nothing for now
		        }else if(parts[0].equals("Rotate")){
		        	//do nothing for now
		        }else{
		        	System.out.println("Could not draw shape");
		        }
            }
            boardMade = true;//This is so we do not keep setting the same values to bound[]
        }
        
        //this method is to make the boundaries boxes of the gizmos
        public void collision(String shape, int x, int y, int width, int height){
        	if(shape.equals("Triangle")){
        		for(int i=0;i<height*width;i++){
	        		bound[x+i%width+((y+i/height))*800]=false;
	        	}
        	
	        }else if(shape.equals("Square")){
	        	//System.out.println("squarebounded");
	        	for(int i=0;i<height*width;i++){
	        		bound[x+i%width+((y+i/height))*800]=false;
	        	}
	        }else if(shape.equals("Circle")){

	        	//System.out.println("circlebounded");
	        	for(int i=0;i<height*width;i++){
	        		bound[x+i%width+((y+i/height))*800]=false;
	        	}
	        }else if(shape.equals("Bumper")){
	        	for(int i=0;i<height*width;i++){
	        		bound[x+i%width+((y+i/height))*800]=false;
	        		}
	        }else if(shape.equals("LeftFlipper")){
	        	for(int i=0;i<height*width;i++){
	        		bound[x+i%width+((y+i/height))*800]=false;
	        		}
	        }else if(shape.equals("RightFlipper")){
	        	for(int i=0;i<height*width;i++){
	        		bound[x+i%width+((y+i/height))*800]=false;
	        		}
	        }else if(shape.equals("Absorber")){
	        	for(int i=0;i<height*width;i++){
	        		bound[x+i%width+((y+i/height))*800]=false;
	        		}
	        }
        }
        /**
         * This method is called when the Timer goes off and we
         * need to move and repaint the ball.
         * @modifies both the ball and the window that this listener owns 
         * @effects causes the ball to move and the window to be updated
         * to show the new position of the ball. 
         */
        private void update() {
            Rectangle oldPos = ball.boundingBox();

            ball.move(); // make changes to the logical animation state

            Rectangle repaintArea = oldPos.union(ball.boundingBox());

            // Have Swing tell the AnimationWindow to run its paint()
            // method.  One could also call repaint(), but this would
            // repaint the entire window as opposed to only the portion that
            // has changed.
            repaint(repaintArea.x, repaintArea.y, repaintArea.width,
                            repaintArea.height);
            
        }

        /**
         * @modifies this
         * @effects Turns the animation on/off. 
         * @param m Boolean indicating if animation is on/off
         */
        public void setMode(boolean m) {

            if (mode == m) {
                // Nothing to do.
                return;
            }

            if (mode == true) {
                // we're about to change mode: turn off all the old listeners
                removeMouseListener(eventListener);
                removeMouseMotionListener(eventListener);
                removeKeyListener(eventListener);
            }

            mode = m;

            if (mode == true) {
                // the mode is true: turn on the listeners
                addMouseListener(eventListener);
                addMouseMotionListener(eventListener);
                addKeyListener(eventListener);
                requestFocus(); // make sure keyboard is directed to us
                timer.start();
            }
            else {
                timer.stop();
            }
        }

        /**
         * Overview: AnimationEventListener is an inner class that 
         * responds to all sorts of external events, and provides the
         * required semantic operations for our particular program.  It 
         * owns, and sends semantic actions to the ball and window of the
         * outer class
         */
        class AnimationEventListener extends MouseAdapter implements
                        MouseMotionListener, KeyListener, ActionListener {

            // MouseAdapter gives us empty methods for the MouseListener
            // interface: mouseClicked, mouseEntered, mouseExited, mousePressed,
            // and mouseReleased.

            /**
             * For this example we only need to override mouseClicked
             * @modifes the ball that this listener owns
             * @effects causes the ball to be bumped in a random direction
             * @param e Detected MouseEvent
             */
            @Override public void mouseClicked(MouseEvent e) {
                ball.randomBump();
            }

            /**
             * MouseMotionListener interface
             * Override this method to act on mouse drag events. 
             * @param e Detected MouseEvent
             */ 
            public void mouseDragged(MouseEvent e) {
            }

            /**
             * MouseMotionListener interface
             * Override this method to act on mouse move events. 
             * @param e Detected MouseEvent
             */ 
            public void mouseMoved(MouseEvent e) {
            }

            /**
             * We implement the KeyListener interface so that we can
			 * bump the ball in a 
             * random direction if keys A-J is presse.
             * @modifies the ball that this listener owns
             * @effects causes the ball to be bumped in a random direction but 
             * only if one of the keys A-J is pressed.
             * @param e Detected Key Press Event
             */
            public void keyPressed(KeyEvent e) {
                // 
                int keynum = e.getKeyCode();

                if ((keynum >= 65) && (keynum <= 74)) {
                    //System.out.println("keypress " + e.getKeyCode());
                    ball.randomBump();
                }
            }
            
            /**
             * Do nothing.
             * @param e Detected Key Released Event
             */
            public void keyReleased(KeyEvent e) {
            }

            /**
             * Do nothing.
             * @param e Detected Key Typed Event
             */
            public void keyTyped(KeyEvent e) {
            }

            /**
             * This is the callback for the timer
             * @param e ActionEvent generated by timer
             */
            public void actionPerformed(ActionEvent e) {
                update();
            }
        }
    }

    /**
     * Overview: An ApplicationWindow is a top level program window that 
     * contains a toolbar and an animation window.
     */
    private static class ApplicationWindow extends JFrame {

        private static final long serialVersionUID = 3257563992905298229L;

        protected AnimationWindow animationWindow;

        /**
         * @effects Initializes the application window so that it contains
         * a toolbar and an animation window.
         */
        public ApplicationWindow() {

            // Title bar
            super("Swing Demonstration Program");

            // respond to the window system asking us to quit
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            //Create the toolbar.
            JToolBar toolBar = new JToolBar();
            addButtons(toolBar);

            //Create the animation area used for output.
            animationWindow = new AnimationWindow();
            // Put it in a scrollPane, (this makes a border)
            JScrollPane scrollPane = new JScrollPane(animationWindow);

            //Lay out the content pane.
            JPanel contentPane = new JPanel();
            contentPane.setLayout(new BorderLayout());
            contentPane.setPreferredSize(new Dimension(510, 530));
            contentPane.add(toolBar, BorderLayout.NORTH);
            contentPane.add(scrollPane, BorderLayout.CENTER);
            setContentPane(contentPane);
        }

        /**
         * @modifies toolBar 
         * @effects adds Run, Stop and Quit buttons to toolBar
         * @param toolBar toolbar to add buttons to.
         */
        protected void addButtons(JToolBar toolBar) {

            JButton button = null;

            button = new JButton("Run");
            button.setToolTipText("Start the animation");
            // when this button is pushed it calls animationWindow.setMode(true)
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    animationWindow.setMode(true);
                }
            });
            toolBar.add(button);

            button = new JButton("Stop");
            button.setToolTipText("Stop the animation");
            // when this button is pushed it calls animationWindow.setMode(false)
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    animationWindow.setMode(false);
                }
            });
            toolBar.add(button);

            button = new JButton("Quit");
            button.setToolTipText("Quit the program");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            toolBar.add(button);
        }
    }
    
    //This is the method reads in the gizmos from a txt file
    public static HashMap<String, String> gizmoBoardReader() {  
    	HashMap<String, String> board = new HashMap<String, String>(); 
    		try{
    		//System.out.println(System.getProperty("user.dir"));
    		   BufferedReader reader = new BufferedReader(	
    		//Here are our different gizmo text files to read in
    		   //new FileReader("testtriangle.txt"));
    		   //new FileReader("testcase1.txt"));
    		   new FileReader("testcase2.txt"));
    		   Scanner scanner=new Scanner(reader);
    		   //rest of reading code here
    		   while(scanner.hasNext()) {
    		        //If all lines have been read while ends
    		       String s = scanner.next();
    		        //splits a line of string into token s and converts them
    		       //System.out.println("s = " +s);
    		      
    		        if(s.equals("Triangle")){
    		        	String name = scanner.next();
    		        	String xposition = scanner.next();
    		        	String yposition = scanner.next();
    		        	String position = "Triangle" + " " + xposition + " " + yposition;
    		        	//System.out.println("position " +position);
    		        	board.put(name,position);
    		        }else if(s.equals("Square")){
    		        	String name = scanner.next();
    		        	String xposition = scanner.next();
    		        	String yposition = scanner.next();
    		        	String position = "Square" + " " + xposition + " " + yposition;
    		        	board.put(name,position);
    		        }else if(s.equals("Circle")){
    		        	String name = scanner.next();
    		        	String xposition = scanner.next();
    		        	String yposition = scanner.next();
    		        	String position = "Circle" + " " + xposition + " " + yposition;
    		        	board.put(name,position);
    		        }else if(s.equals("Bumper")){
    		        	String name = scanner.next();
    		        	String xposition = scanner.next();
    		        	String yposition = scanner.next();
    		        	String position = "Bumper" + " " + xposition + " " + yposition;
    		        	board.put(name,position);
    		        }else if(s.equals("LeftFlipper")){
    		        	String name = scanner.next();
    		        	String xposition = scanner.next();
    		        	String yposition = scanner.next();
    		        	String position = "LeftFlipper" + " " + xposition + " " + yposition;
    		        	board.put(name,position);
    		        }else if(s.equals("RightFlipper")){
    		        	String name = scanner.next();
    		        	String xposition = scanner.next();
    		        	String yposition = scanner.next();
    		        	String position = "RightFlipper" + " " + xposition + " " + yposition;
    		        	board.put(name,position);
    		        }else if(s.equals("Absorber")){
    		        	String name = scanner.next();
    		        	String xposition = scanner.next();
    		        	String yposition = scanner.next();
    		        	String position = "Absorber" + " " + xposition + " " + yposition;
    		        	board.put(name,position);
    		        }else if(s.equals("KeyConnect")){
    		        	//must finish coming back
    		        	String foo = scanner.next();
    		        	 foo = scanner.next();
    		        	 foo = scanner.next();
    		        	 foo = scanner.next();
    		        	
    		        }else if(s.equals("Connect")){
    		        	//must finish coming back
    		        	String foo = scanner.next();
    		        	 foo = scanner.next();
    		        	 foo = scanner.next();
    		        	
    		        }else if(s.equals("Rotate")){
    		        	//must finish coming back
    		        	String foo = scanner.next();
    		        }else{
    		        	System.out.println("Could not read shape file in properly -Board Reader-");
    		        }
    		        
    		   }
    		   reader.close();
    		   scanner.close();
    		}catch(Exception e){
    		   System.out.println("Error! (In reading board)");
    		}
    		//System.out.println(board);
    	    return board;
    	  }
}
