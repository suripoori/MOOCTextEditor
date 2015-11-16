package textgen;

import java.util.AbstractList;
import java.lang.IndexOutOfBoundsException;

/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
		head.prev = null;
		tail.next = null;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		add(size, element);
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		LLNode<E> it = getNode(index);
		return it.data;
	}

	public LLNode<E> getNode(int index){
		if(index < 0){
			throw new IndexOutOfBoundsException("Provided index is less than 0");
		}
		if(index >= size){
			throw new IndexOutOfBoundsException("Provided index is larger than the size of the list");
		}
		LLNode<E> it = head;
		for (int i=0; i<=index; i++){
			it = it.next;
			if(it == null){
				throw new IndexOutOfBoundsException("Provided index is larger than the number of elements in the List");
			}
		}
		return it;
	}
	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (index < 0){
			throw new IndexOutOfBoundsException("Provided index is lesser than 0");
		}
		if (index > size){
			throw new IndexOutOfBoundsException("Provided index is larger than the number of elements in the List");
		}
		if (element == null){
			throw new NullPointerException("Provided element value is null");
		}
		if (index < size/2){
			LLNode<E> prev = head;
			for (int i=0; i<index; i++){
				prev = prev.next;
				if (prev == null){
					throw new NullPointerException("Something went wrong. The element at this index is null " + Integer.toString(i));
				}
			}
			LLNode<E> newNode = new LLNode(element, prev);
		}
		else {
			LLNode<E> next = tail;
			for (int i=size; i>=index; i--){
				next = next.prev;
				if (next == null){
					throw new NullPointerException("Something went wrong. The element at this index is null " + Integer.toString(i));
				}
			}
			LLNode<E> newNode = new LLNode(element, next);
		}
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index < 0){
			throw new IndexOutOfBoundsException("Provided index is lesser than 0");
		}
		if (index >= size){
			throw new IndexOutOfBoundsException("Provided index is larger than the number of elements in the List");
		}
		LLNode<E> prev = head;
		for (int i=0; i<=index; i++){
			prev = prev.next;
		}
		if (prev == null){
			throw new NullPointerException("Something went wrong, null element at index " + Integer.toString(index));
		}
		E return_data = prev.data;
		prev.prev.next = prev.next;
		prev.next.prev = prev.prev;
		size--;
		return return_data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (index < 0){
			throw new IndexOutOfBoundsException("Provided index is lesser than 0");
		}
		if (index >= size){
			throw new IndexOutOfBoundsException("Provided index is larger than the number of elements in the List");
		}
		if (element == null){
			throw new NullPointerException("Provided element value is null");
		}
		LLNode<E> prev = head;
		for (int i=0; i<index; i++){
			prev = prev.next;
		}
		
		E return_data = prev.next.data;
		prev.next.data = element;
		return return_data;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e, LLNode<E> p){
		this(e);
		this.prev = p;
		this.next = p.next;
		p.next.prev = this;
		p.next = this;
	}
	
}
