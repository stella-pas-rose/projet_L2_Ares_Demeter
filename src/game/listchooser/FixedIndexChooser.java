package game.listchooser;

import java.util.List;

/**
 * a listChooser that always chooses the item at a fixed index
 * useful for testing 
 */
public class FixedIndexChooser<T> implements ListChooser<T> {

    private final int index;

    /**
     * constructs a chooser that always selects the item at the given index
     *
     * @param index the index of the item to select from the list
     */
    public FixedIndexChooser(int index) {
        this.index = index;
    }

    @Override
    public T choose(String msg, List<? extends T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException("Fixed index " + index + " is out of bounds for list size " + list.size());
        }
        return list.get(index);
    }
}
