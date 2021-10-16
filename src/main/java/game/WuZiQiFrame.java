package game;

import net.CancelReadyMsg;
import net.Client;
import net.PutMsg;
import net.ReadyMsg;

import java.awt.*;
import java.awt.event.*;

public class WuZiQiFrame extends Frame {
    private static final WuZiQiFrame INSTANCE = new WuZiQiFrame();
    GameModel gm = GameModel.getInstance();
    Client client = Client.getInstance();

    private WuZiQiFrame() throws HeadlessException {
        new Thread(() -> client.start()).start();

        while (client.getChannel() == null);

        gm.self.setAddress(client.getChannel().localAddress().toString());

        this.setTitle(gm.self.getNickname() + " " + gm.self.getGroup().toString());
        this.setVisible(true);
        this.setSize(800, 600);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addMouseListener(new MyMouseListener());
        this.setLayout(null);
        Button button = new Button("ready");
        disableButton(button);
        button.setSize(80, 40);
        button.setLocation(650, 500);
        button.setVisible(true);
        button.addActionListener(new ReadyButtonListener(button));
        Label self = new Label("self: " + gm.self.getAddress());
        Label opponent = new Label("opponent: ");
        getOpponentAddress(opponent);
        self.setLocation(625, 400);
        opponent.setLocation(625, 200);
        self.setSize(200, 40);
        opponent.setSize(200, 40);
        self.setVisible(true);
        opponent.setVisible(true);
        this.add(button);
        this.add(self);
        this.add(opponent);
    }

    private void getOpponentAddress(Label label) {
        new Thread(() -> {
            while (true) {
                if (gm.opponent.getAddress() != null) {
//                        System.out.println(1);
                    label.setText("opponent: " + gm.opponent.getAddress());
                } else {
                    label.setText("opponent: ");
                }
            }
        }).start();
    }

    private void disableButton(Button button) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);//不知道为啥要停一下才生效
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(gm.isGoing == 2)  {
                    button.setEnabled(false);
                    break;
                }
            }
        }).start();
    }

    public static WuZiQiFrame getInstance() {
        return INSTANCE;
    }

    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (gm.isGoing != 2) return;
            if ((gm.self.getGroup() == Group.WHITE && gm.turn % 2 == 0) || (gm.self.getGroup() == Group.BLACK && gm.turn % 2 == 1)) {
                return;
            }
            gm.put(e.getX(), e.getY(), false);
            repaint();
            client.send(new PutMsg(e.getX(), e.getY()));
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    class ReadyButtonListener implements ActionListener {
        Button button;

        public ReadyButtonListener(Button button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(button.getLabel().equals("ready")) {
                gm.isGoing++;
                client.send(new ReadyMsg());
                button.setLabel("cancel");
            } else {
                gm.isGoing--;
                client.send(new CancelReadyMsg());
                button.setLabel("ready");

            }
        }
    }
}
