import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class TestFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public TestFrame() {
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel testPanel = new JPanel();
		JButton testButton = new JButton("I am a test Button");
		final JTextField testText = new JTextField(25);
		testText.setText("This is some test text");
		this.add(testPanel);
		testPanel.add(testButton);
		testPanel.add(testText);
		
		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				testText.setText("This is some new test text that has been changed");
			
				
			}
			
		});
		
		
	}
	
	public static void main (String[] args) {
		SwingUtilities.invokeLater(new Runnable () {
			public void run() {
				TestFrame test1 = new TestFrame();
				test1.setVisible(true);
			}
		});
	

	}

}
