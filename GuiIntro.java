import java.awt. *;
import java.awt.event.*;
import javax.swing.JFrame;
public class GuiIntro implements ActionListener {

    Button buttonPtr;

    public static void main(String[] args) {
        GuiIntro guiIntroPtr = new GuiIntro(); //create object of GuiIntro
        guiIntroPtr.go();
    }

    void go() {
        this.buttonPtr =new Button("CLICK THIS BUTTON");
        this.buttonPtr.addActionListener(this);
        JFrame framePtr = new JFrame(); //get a pointer out to object of JFrame
        framePtr.setSize(300,300);
        Container containerPtr = framePtr.getContentPane();
        containerPtr.add(buttonPtr);
        framePtr.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        this.buttonPtr.setLabel("I HAVE BEEN CLICKED! ");
    }
}
