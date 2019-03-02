import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BeatBox
{

	JFrame theFrame;

	JPanel mainPanel;

	JList inconingList;

	JTextField userMessage;

	ArrayList<JCheckBox> checkboxList;

	int nextNum;

	Vector<String> listVector = new Vector<String>();

	String userName;

	ObjectOutputStream out;

	ObjectInputStream in;

	HashMap<String, boolean[]> otherSeqsMap = new HashMap<String, boolean[]>();

	Sequencer sequencer;

	Sequence sequence;

	Sequence mySequence = null;

	Track track;

	String[] instrumentNames = {
		"Bass Drum",
		"Closed Hi-Hat",
		"Open Hi-Hat",
		"Acoustic Snare",
		"Crash Cymbal",
		"Hand Clap",
		"High Tom",
		"Hi Bongo",
		"Maracas",
		"Whistle",
		"Low Conga",
		"Cowbell",
		"Vibraslap",
		"Low-mid Tom",
		"High Agogo",
		"Open Hi Conga"
	};

	int[] instruments = {
		35,
		42,
		46,
		38,
		49,
		39,
		50,
		60,
		70,
		72,
		64,
		56,
		58,
		47,
		67,
		63
	};

	public static void main(String[] args)
	{
		new BeatBox().startUP(args[0]);;
	}

	public void startUP(String name)
	{
		userName = name;
		// open a server conection
		try
		{
			Socket sock = new Socket("127.0.0.1", 4242);
			out = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			Thread remote = new Thread(new RemoteReader());
			remote.start();
		}
		catch (Exception e)
		{
			System.out.println("Couldn't connect - you'll have to play alone");
		}

		setUpMidi();
		buildGUI();
	}

	public void buildGUI()
	{
		theFrame = new JFrame("Cyber BeatBox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		checkboxList = new ArrayList<JCheckBox>();
		Box buttonBox = new Box(BoxLayout.Y_AXIS);

		// criação dos botões, atribuição de ação para eles (Listener) e adicionamento deles no box
		JButton start = new JButton("Start");
		start.addActionListener(new MyStartListener());
		buttonBox.add(start);

		JButton stop = new JButton("Stop");
		stop.addActionListener(new MyStopListener());
		buttonBox.add(stop);

		JButton upTempo = new JButton("Tempo up");
		upTempo.addActionListener(new MyUpTempoListener());
		buttonBox.add(upTempo);

		JButton downTempo = new JButton("Tempo down");
		downTempo.addActionListener(new MyDownTempoListener());
		buttonBox.add(downTempo);

		JButton serializelt = new JButton("sendIt");
		serializelt.addActionListener(new MySendListener());
		buttonBox.add(serializelt);

		JButton restore = new JButton("Restore");
		restore.addActionListener(new MyReadInListener());
		buttonBox.add(restore);

		userMessage = new JTextField();
		buttonBox.add(userMessage);

		inconingList = new JList();
		inconingList.addListSelectionListener(new MyListSelectionListener());
		inconingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane theList = new JScrollPane(inconingList);
		buttonBox.add(theList);
		inconingList.setListData(listVector);

		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for (int i = 0; i < 16; i++)
		{
			nameBox.add(new Label(instrumentNames[i]));
		}

		background.add(BorderLayout.EAST, buttonBox);
		background.add(BorderLayout.WEST, nameBox);

		theFrame.getContentPane().add(background);

		GridLayout grid = new GridLayout(16, 16);
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER, mainPanel);

		for (int i = 0; i < 256; i++)
		{
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
		}

		// setUpMidi();

		theFrame.setBounds(50, 50, 300, 300);
		theFrame.pack();
		theFrame.setVisible(true);

	}

	public void setUpMidi()
	{
		try
		{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void buildTrackAndStart()
	{
		ArrayList<Integer> trackList = null;

		sequence.deleteTrack(track);
		track = sequence.createTrack();

		for (int i = 0; i < 16; i++)
		{
			trackList = new ArrayList<Integer>();

			for (int j = 0; j < 16; j++)
			{
				JCheckBox jc = checkboxList.get(j + (16 * i));
				if (jc.isSelected())
				{
					int key = instruments[i];
					trackList.add(new Integer(key));
				}
				else
				{
					trackList.add(null);
				}
			}

			makeTracks(trackList);
		}

		track.add(makeEvent(192, 9, 1, 0, 15));

		try
		{
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	public class MyStartListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			buildTrackAndStart();
		}

	}

	public class MyStopListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			sequencer.stop();
		}

	}

	public class MyUpTempoListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float) (tempoFactor * 1.03));
		}

	}

	public class MyDownTempoListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float) (tempoFactor * .97));
		}

	}

	public class MySendListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			boolean[] checkboxState = new boolean[256];
			for (int i = 0; i < 256; i++)
			{
				JCheckBox check = checkboxList.get(i);
				if (check.isSelected())
				{
					checkboxState[i] = true;
				}
			}
			String messageToSend = null;
			try
			{
				out.writeObject(userName + " '" + nextNum++ + "': " + userMessage.getText());
				out.writeObject(checkboxState);
			}
			catch (Exception ex)
			{
				System.out.println("Sorry dude. Could not send it to the server.");
			}
			userMessage.setText("");
		}

	}

	public class MyListSelectionListener implements ListSelectionListener
	{

		@Override
		public void valueChanged(ListSelectionEvent le)
		{
			if (!le.getValueIsAdjusting())
			{
				String selected = (String) inconingList.getSelectedValue();
				if (selected != null)
				{
					boolean[] selectedState = otherSeqsMap.get(selected);
					changeSequence(selectedState);
					sequencer.stop();
					buildTrackAndStart();
				}
			}
		}

	}

	public class RemoteReader implements Runnable
	{

		boolean[] checkboxState = null;

		String nameToShow = null;

		Object obj = null;

		@Override
		public void run()
		{
			try
			{
				while ((obj = in.readObject()) != null)
				{
					System.out.println("got an object from server");
					System.out.println(obj.getClass());
					String nameToShow = (String) obj;
					checkboxState = (boolean[]) in.readObject();
					otherSeqsMap.put(nameToShow, checkboxState);
					listVector.add(nameToShow);
					inconingList.setListData(listVector);
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

	}

	public class MyPlayMineListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (mySequence != null)
			{
				sequence = mySequence;
			}
		}

	}

	public void changeSequence(boolean[] checkboxState)
	{
		for (int i = 0; i < 256; i++)
		{
			JCheckBox check = checkboxList.get(i);
			if (checkboxState[i])
			{
				check.setSelected(true);
			}
			else
			{
				check.setSelected(false);
			}
		}
	}

	public void makeTracks(ArrayList list)
	{
		Iterator it = list.iterator();
		for (int i = 0; i < 16; i++)
		{
			Integer num = (Integer) it.next();

			if (num != null)
			{
				int numKey = num.intValue();
				track.add(makeEvent(144, 9, numKey, 100, i));
				track.add(makeEvent(128, 9, numKey, 100, i + 1));
			}
		}
	}

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick)
	{
		MidiEvent event = null;
		try
		{
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return event;
	}

	// public class MySendListener implements ActionListener
	// {
	//
	// @Override
	// public void actionPerformed(ActionEvent e)
	// {
	// boolean[] checkboxState = new boolean[256];
	//
	// for (int i = 0; i < 256; i++)
	// {
	// JCheckBox check = checkboxList.get(i);
	// if (check.isSelected())
	// {
	// checkboxState[i] = true;
	// }
	// }
	//
	// try
	// {
	// JFileChooser fileSave = new JFileChooser();
	// fileSave.showSaveDialog(theFrame);
	// FileOutputStream fileStream = new FileOutputStream(fileSave.getSelectedFile());
	// ObjectOutputStream os = new ObjectOutputStream(fileStream);
	// os.writeObject(checkboxState);
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	// }
	// }
	//
	// }

	public class MyReadInListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			boolean[] cheackboxState = null;

			try
			{
				JFileChooser fileOpen = new JFileChooser();
				fileOpen.showOpenDialog(theFrame);
				FileInputStream fileIn = new FileInputStream(fileOpen.getSelectedFile());
				ObjectInputStream is = new ObjectInputStream(fileIn);
				cheackboxState = (boolean[]) is.readObject();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

			for (int i = 0; i < 256; i++)
			{
				JCheckBox check = checkboxList.get(i);
				if (cheackboxState[i])
				{
					check.setSelected(true);
				}
				else
				{
					check.setSelected(false);
				}
			}

			sequencer.stop();
			buildTrackAndStart();
		}

	}

}
