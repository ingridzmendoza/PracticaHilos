import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DrawPad extends JComponent implements Runnable {
    Image image;
    Graphics2D graphics2D;
    int currentX, currentY, oldX, oldY;
    int deltaX = 10;
    int deltaY = 10;

    int cX = 10;
    int cY = 10;

    public DrawPad() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                if (graphics2D != null)
                    graphics2D.drawLine(oldX, oldY, currentX, currentY);
                repaint();
                oldX = currentX;
                oldY = currentY;
            }
        });
        Thread animator = new Thread(this,"Animator");
        animator.start();
    }

    public void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
          clear();
        }
        g.drawImage(image, 0, 0, null);
        graphics2D.drawOval(cX, cY, 50, 50);

    }

    public void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.black);
        repaint();
    }

    @Override
    public void run() {

        while(true) {
            if( cX > getSize().width && cX < 0 ) {
                deltaX = deltaX * -1;
            }
            if( cY > getSize().height && cY < 0 ) {
                deltaY = deltaY * -1;
            }

            cX = cX + deltaX;
            cY = cY + deltaY;
            repaint();
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
