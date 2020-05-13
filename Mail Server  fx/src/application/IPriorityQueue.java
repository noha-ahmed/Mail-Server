package application;

public interface IPriorityQueue {
	public void insert(Object item, int key);
	public Object removeMin();
	public Object min();
	public boolean isEmpty();
	public int size();
}
