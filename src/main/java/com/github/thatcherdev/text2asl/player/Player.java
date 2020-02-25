package com.github.thatcherdev.text2asl.player;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;

public class Player {

    private String[] videos = null;
    private VideoPanel panel = null;

    public Player(String[] videos) {
        this.videos = videos;
        panel = new VideoPanel();
        panel.setPreferredSize(new Dimension(640, 380));
    }

    public JPanel getPanel() {
        return panel;
    }

    /**
     * Plays the videos in {@link #videos} on VideoPanel {@link #panel}.
     * 
     * @throws InterruptedException
     */
    public void playVideos() throws InterruptedException {
        for (String video : videos) {
            IMediaReader reader = ToolFactory.makeReader(video);
            reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

            MediaListenerAdapter adapter = new MediaListenerAdapter() {
                @Override
                public void onVideoPicture(IVideoPictureEvent event) {
                    panel.setImage((BufferedImage) event.getImage());
                }
            };
            reader.addListener(adapter);

            while (reader.readPacket() == null)
                do
                    Thread.sleep(10);
                while (false);
        }
    }

    private class VideoPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        private Image image;

        public void setImage(Image image) {
            VideoPanel.this.image = image;
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            if (image != null)
                g.drawImage(image, 0, 0, null);
        }
    }
}