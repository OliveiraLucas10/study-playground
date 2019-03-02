import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MiniMiniMusicApp
{

	public static void main(String[] args)
	{
		MiniMiniMusicApp mini = new MiniMiniMusicApp();
		mini.play();

	}

	private void play()
	{
		try
		{
			Sequencer player = MidiSystem.getSequencer();
			player.open();

			Sequence seq = new Sequence(Sequence.PPQ, 4);

			Track track = seq.createTrack();

			ShortMessage a = new ShortMessage();
			a.setMessage(144, 1, 20, 100);
			MidiEvent noteOn = new MidiEvent(a, 1);
			track.add(noteOn);

			ShortMessage c = new ShortMessage();
			a.setMessage(192, 1, 102, 0);
			MidiEvent noteOnc = new MidiEvent(c, 2);
			track.add(noteOnc);

			ShortMessage b = new ShortMessage();
			b.setMessage(128, 1, 20, 100);
			MidiEvent noteOff = new MidiEvent(b, 3);
			track.add(noteOff);

			player.setSequence(seq);

			player.start();
			Thread.sleep(1000 * 2);
			player.close();
			System.exit(0);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

}
