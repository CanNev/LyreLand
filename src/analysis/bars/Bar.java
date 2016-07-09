package analysis.bars;

import java.util.ArrayList;

/**
 * Class that describes a bar
 */
public class Bar {
    private ArrayList<BarNote> notes_;
    private ArrayList<ArrayList<Integer>> notesByBeat_;

    /**
     * Constructor for an empty Bar.
     */
    public Bar() {
        this.notes_ = new ArrayList<>();
        this.notesByBeat_ = null;
    }

    /**
     * Constructor for a Bar with a set of BarNotes
     * @param notes_ Set of BarNotes of the Bar
     */
    public Bar(ArrayList<BarNote> notes_) {
        this.notes_ = notes_;
    }

    /**
     * Add a note to the Bar
     * @param note BarNote to insert
     */
    public void addNote(BarNote note) {
        notes_.add(note);
    }

    /**
     * Getter for the BarNotes contained in the Bar
     * @return List of BarNotes
     */
    public ArrayList<BarNote> getNotes() {
        return notes_;
    }

    public void segmentRhythms(double quantum) {
        int size = notes_.size();
        for (int i = 0; i < size; ++i) {
            BarNote barNote = notes_.get(i);
            while (barNote.getDuration() > quantum) {
                double newNoteStartTime = barNote.getStartTime() + barNote.getDuration() - quantum;
                notes_.add(new BarNote(newNoteStartTime, quantum, barNote.getPitch()));
                barNote.setDuration(barNote.getDuration() - quantum);
            }
        }
    }

    /**
     * Reorganize notes in a List of List of BarNotes that groupes the notes by beat in the bar
     * @param beatsPerBar Number of beats in a bar
     * @param barUnit Beat rhythmic value
     */
    public void groupNotesByBeat(int beatsPerBar, double barUnit) {
        notesByBeat_ = new ArrayList<>();
        for (int i = 0; i < beatsPerBar; ++i)
            notesByBeat_.add(new ArrayList<>());
        for (BarNote barNote : notes_) {
            int barBeat = (int) (barNote.getStartTime() / barUnit);
            notesByBeat_.get(barBeat).add(barNote.getPitch());
        }
    }

    /**
     * Getter for the beat-organized BarNotes
     * @return List of List of BarNotes (each sublist corresponds to a beat)
     */
    public ArrayList<ArrayList<Integer>> getNotesByBeat() {
        return notesByBeat_;
    }
}
