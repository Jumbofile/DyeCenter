package Sqldemo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import Sqldemo.SQLDemo;

public class S_Main {
	
	public static JTextArea consoleWin = new JTextArea();
	public static boolean sqlOn = false;
	private static ArrayList<String> history = new ArrayList<String>();
	private static int index = -1;
	
	public static void main(String[] args) {
		//Initialize the server
		//S_Database db = new S_Database();
		
		//Start server gui
		JFrame frame = new JFrame("SQLDemo");
		//JTextField consoleBox = new JTextField();
		JTextArea sqlTextBox = new JTextArea();
		JPanel panel = new JPanel();

		JScrollPane scroll = new JScrollPane (consoleWin);
		JScrollPane scroll2 = new JScrollPane (sqlTextBox);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1800, 604);
		frame.setResizable(false);

		consoleWin.setEditable(false);
		consoleWin.setFont(new Font("Consolas", Font.PLAIN, 12));  // make a new font object);
		scroll.setBounds(10,10,1775,420);
		consoleWin.setLineWrap(true);
		sqlTextBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		//consoleBox.setBackground(Color.BLUE);
		sqlTextBox.setLineWrap(true);
		//sqlTextBox.setBounds(10,432,775,104);
		scroll2.setBounds(10,432,875,104);
		frame.getContentPane().add(scroll);
		frame.getContentPane().add(scroll2);
		
		JButton button = new JButton();
		button.setBounds(10, 538, 100, 30);
		button.setText("Submit");

		
		//is enter hit
        Action action = new AbstractAction()
        {
            /**
			 * eclipse made me do this
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e)
            {
				String statement = sqlTextBox.getText();
				history.add(statement);
				try {
					Sqldemo.SQLDemo.accessDemo(statement);
				}catch(Exception e1){
					consoleWin.append("Invalid statement \n");
				}
                index = history.size() - 1;
				//consoleWin.append(statement);
                sqlTextBox.setText("");
                
        	} 
        };
        
        sqlTextBox.addKeyListener(new KeyListener() {            
        	@Override
            public void keyPressed(KeyEvent e) {
        		if(index == -1) {
        			index = 0;
        		}
        		if(index == history.size()) {
        			index--;
        		}
                System.out.println("test");
                switch( e.getKeyCode()) { 
                case KeyEvent.VK_UP:
                	System.out.println(index);
                	if(index> -1) {
	                    sqlTextBox.setText(history.get(index));
	                    index--;
                	}
                    break;
                case KeyEvent.VK_DOWN:
                	if(index < history.size()) {
                		System.out.println(index);
	                    sqlTextBox.setText(history.get(index));
	                    index++;
	                    
                	}
                    break;
             }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub      
            }



			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        //jframe stuff
        button.addActionListener( action );
		//frame.getContentPane().add(sqlTextBox);
        frame.getContentPane().add(button);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		
    }
}

