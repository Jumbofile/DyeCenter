package Sqldemo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.Keymap;

import Sqldemo.SQLDemo;

public class S_Main {
	
	public static JTextArea consoleWin = new JTextArea();
	public static boolean sqlOn = false;
	private static ArrayList<String> history = new ArrayList<String>();
	private static int index = -1;
	private static int width = 1800;
	
	public static void main(String[] args) {
		//Initialize the server
		//S_Database db = new S_Database();
		
		//Start server gui
		JFrame frame = new JFrame("SQLDemo");
		//JTextField consoleBox = new JTextField();
		JTextArea sqlTextBox = new JTextArea();
		JPanel panel = new JPanel();

		//Adds scroll to the console
		JScrollPane scroll = new JScrollPane (consoleWin);
		JScrollPane scroll2 = new JScrollPane (sqlTextBox);

		//Adds the exit button on top right
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, 604);

		//Not resizable
		frame.setResizable(false);

		consoleWin.setEditable(false);
		consoleWin.setFont(new Font("Consolas", Font.PLAIN, 12));  // make a new font object);
		scroll.setBounds(10,10,width - 25,420);
		consoleWin.setLineWrap(true);
		sqlTextBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		sqlTextBox.setLineWrap(true);
		scroll2.setBounds(10,432,875,104);
		frame.getContentPane().add(scroll);
		frame.getContentPane().add(scroll2);

		//Adds enter button
		JButton enterButton = new JButton();
		enterButton.setBounds(10, 538, 100, 30);
		enterButton.setText("Submit");

		//adds copy button
		JButton copyButton = new JButton();
		copyButton.setBounds(115, 538, 100, 30);
		copyButton.setText("Copy");

		
		//is enter hit
        Action enterAction = new AbstractAction()
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
					e1.printStackTrace();
					consoleWin.append("Invalid statement \n");
				}
                index = history.size() - 1;
				//consoleWin.append(statement);
                //sqlTextBox.setText("");
                
        	} 
        };

		//is copy hit
		Action copyAction = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//select all text in the textarea
				sqlTextBox.selectAll();

				//Copy all text in the textarea
				sqlTextBox.copy();

				//Update console so you know something happened
				consoleWin.append("Copied text.");
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

                switch( e.getKeyCode()) {
                case KeyEvent.VK_UP:
                	/*System.out.println(index);
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
	                    
                	}*/
                    break;
             }

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }



			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
        });
        
        //Place objects on the panel
        enterButton.addActionListener( enterAction );
        frame.getContentPane().add(enterButton);
		copyButton.addActionListener( copyAction );
		frame.getContentPane().add(copyButton);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		
    }
}

