/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structures;

/**
 *
 * @author brian
 */


/**Enum useful for side's selection in adding stuff.*/
enum Side{ Front,Back }

/**List Class*/
public class List<T>{
	
	/**Node class needed as a principal structure.*/
	public class Node{
		
		//Private attributes
		
		/**The value 'T' to save.*/
		private T _value; 
		/**The previous node's pointer.*/
		private Node _previous; 
		/**The next node's pointer.*/
		private Node _next; 
		
		//Public constructors
		
		/**Creates a new node using just the 'T' value. Previous and Next node pointers will be setting to null value.*/
		public Node(T value) {
			this._value = value;
			this._previous = this._next = null;
		}
		
		/**Creates a new node using the 'T' value, the Previous and Next node pointers.*/
		public Node(T value, Node previous, Node next) {
			this._value = value;
			this._previous = previous;
			this._next = next;
		}
		
		//Public methods
		
		/**Sets new 'T' value.*/
		public void Value(T value) { this._value = value;}
		
		/**Returns the 'T' value.*/
		public T Value() {
			if(this._value != null)
				return this._value;
			else
				throw new NullPointerException("Error: The Value Is Null. Impossible To Return It.");	
		}
		
		/**Sets new previous node's value.*/
		public void Previous(Node previous) {this._previous = previous;}
		
		/**Returns previous node's value.*/
		public Node Previous() {return this._previous;}
		
		/**Sets new next node's value.*/
		public void Next(Node next) {this._next = next;}
		
		/**Returns next node's value.*/
		public Node Next() {return this._next;}
		
		//Overrides methods
		
		@Override
		public boolean equals(Object object) {
			
			if (object == this) 
	            return true; 
	        if (!(object instanceof List.Node))
	            return false; 
	        
	        @SuppressWarnings("unchecked")
	        Node objectNode = (Node) object;

			if(this._next != null && objectNode._next != null){
				if(this._previous != null && objectNode._previous != null){
					return this._value.equals(objectNode._value) &&
						this._next._value.equals(objectNode._next._value) && this._previous._value.equals(objectNode._previous._value);
				}
				return this._value.equals(objectNode._value) &&
					this._next._value.equals(objectNode._next._value);
			}
			
			if(this._previous != null && objectNode._previous!= null){
				if(this._next != null && objectNode._next != null){
					return this._value.equals(objectNode._value) &&
							this._next._value.equals(objectNode._next._value) && this._previous._value.equals(objectNode._previous._value);
				}
				return this._value.equals(objectNode._value) &&
					this._previous._value.equals(objectNode._previous._value);
			}
			
			return this._value.equals(objectNode._value);
			
		}
	
		@Override
		public String toString()
		{
			if(this._next != null)
				return this._previous != null ? "["+this._previous._value + " <- "  + this._value + " -> " + this._next._value +"]" : "[<- " + this._value +" -> "+ this._next._value+ "]";
			
			if(this._previous != null)
				return this._next != null ? "["+this._previous._value + " <- "+ this._value + " -> " + this._next._value +"]": "["+ this._previous._value +" <- "+ this._value +" ->]";
			
			//Previous & Next Node are null
			return "[<- "+this._value+" ->]";
		}
		
		@Override
		protected void finalize() throws Throwable {
			this._value =  null;
			this._next = this._previous = null;
			System.gc();
		}
	}
	
	//Private list's attributes.
	
	/**Front and first node of the list.*/
	private Node _front; 
	/**Back and last node of the list.*/
	private Node _back; 
	/**The number of elements into the list.*/
	private Integer _size; 
	/**Attribute who tells if the list is empty or not.*/
	private boolean _empty; 
	/**The node who's located at the middle of the list.*/
	private Node _middle; 
	
	//Private methods
	
	/**Sets the initial values of all attributes.*/
	private void Init() {
		this._front = this._back = this._middle = null;
		this._size = 0;
		this._empty = true;
	}
	
	/**Gets the node who has the given element.*/
	private Node GetNode(T element) {
		switch(this._size) {
		case 0:
			return null;
		case 1:
			return (this._front.Value().equals(element))? this._front : null;
		case 2:
			
			if(this._front.Value().equals(element))
				return this._front;
			if(this._back.Value().equals(element))
				return this._back;
			
			return null;
		default:{
				Node first = this._front;
				Node last = this._back;
				double middleIndex = ((double)this._size)/2;
				
				for(double i = 0; i < middleIndex;i++) {
					if(first.Value().equals(element))
						return first;
					if(last.Value().equals(element))
						return last;
					first = first.Next();
					last = last.Previous();
				}
				
				return null;
			}
		}
	}
	
	/**Gets a pair of the nodes who have the given elements.*/
	private Pair<Node,Node> GetNodes(T firstElement, T secondElement){
		Node firstNode;
		Node secondNode;
		firstNode = secondNode = null;
		if(this._size == 1) {
			if(this._front.Value().equals(firstElement))
				firstNode = this._front;
			else if(this._front.Value().equals(secondElement))
				secondNode = this._front;
		}
		else if(this._size == 2) {
			if(this._front.Value().equals(firstElement))
				firstNode = this._front;
			else if(this._front.Value().equals(secondElement))
				secondNode = this._front;
			if(this._back.Value().equals(firstElement))
				firstNode = this._back;
			else if(this._back.Value().equals(secondElement))
				secondNode = this._back;
		}
		else{
			Node first = this._front;
			Node last = this._back;
			double middleIndex = ((double)this._size)/2;
			for(double i = 0; i < middleIndex;i++) {
				if(first.Value().equals(firstElement))
					firstNode = first;
				else if(first.Value().equals(secondElement))
					secondNode = first;
				if(last.Value().equals(firstElement))
					firstNode = last;
				else if(last.Value().equals(secondElement))
					secondNode = last;
				first = first.Next();
				last = last.Previous();
			}
			return null;
		}
		return new Pair<Node,Node>(firstNode, secondNode);
	}

	/**Gets a node from a given index.*/
	private Node GetNode(Integer index) {
		if(!this._empty){
			Integer realIndex = index % this._size;
			if(realIndex == 0) 
				return this._front;
			else if(realIndex == this._size-1) 
				return this._back;
			else { 
				Integer middleIndex = this._size/2;
				if(this._size % 2 != 0) { 
					Integer differenceBetweenFront = Math.abs(realIndex-1);
					Integer differenceBetweenBack = Math.abs(realIndex-(this._size-2));
					Integer differenceBetweenMiddle = Math.abs(realIndex-middleIndex);
					
					if( (differenceBetweenFront < differenceBetweenBack 
							&& differenceBetweenFront < differenceBetweenMiddle ) 
							|| differenceBetweenFront == differenceBetweenMiddle) {
						Node auxiliar = this._front.Next();
						for(Integer i = 1; i < realIndex;i++)
							auxiliar = auxiliar.Next();
						return auxiliar;
					}
					
					if(differenceBetweenMiddle < differenceBetweenBack 
							&& differenceBetweenMiddle < differenceBetweenFront) {	
						if(realIndex > middleIndex) {
							Node auxiliar = this._middle.Next();
							for(Integer i = middleIndex+1; i < realIndex;i++)
								auxiliar = auxiliar.Next();
							return auxiliar;
						}
						else if(realIndex == middleIndex)
							return this._middle;
						else {
							Node auxiliar = this._middle.Previous();
							for(Integer i = middleIndex-1; i > realIndex;i--)
								auxiliar = auxiliar.Previous();
							return auxiliar;
						}
					}

					if((differenceBetweenBack < differenceBetweenMiddle 
							&& differenceBetweenBack < differenceBetweenFront) 
							|| differenceBetweenBack == differenceBetweenMiddle ) {
					
						Node auxiliar = this._back.Previous();
						
						for(Integer i = this._size-2; i > realIndex;i--)
							auxiliar = auxiliar.Previous();
						
						return auxiliar;
					}

				}
				else { 
					Integer differenceBetweenFront = Math.abs(realIndex-1);
					Integer differenceBetweenBack = Math.abs(realIndex-(this._size-2));
					Integer differenceBetweenMiddle = Math.abs(realIndex-(middleIndex-1));
	
					if( (differenceBetweenFront < differenceBetweenBack 
							&& differenceBetweenFront < differenceBetweenMiddle ) 
							|| differenceBetweenFront == differenceBetweenMiddle) {

						Node auxiliar = this._front.Next();
						
						for(Integer i = 1; i < realIndex;i++)
							auxiliar = auxiliar.Next();
						
						return auxiliar;
					}
					
					if(differenceBetweenMiddle < differenceBetweenBack 
							&& differenceBetweenMiddle < differenceBetweenFront) {

						if(realIndex > (middleIndex-1)) {
							
							Node auxiliar = this._middle.Next();
							
							for(Integer i = middleIndex; i < realIndex;i++)
								auxiliar = auxiliar.Next();
							
							return auxiliar;
						}
						else if(realIndex == (middleIndex-1))
							return this._middle;
						else {
							Node auxiliar = this._middle.Previous();
							
							for(Integer i = middleIndex-2; i > realIndex;i--)
								auxiliar = auxiliar.Previous();
							
							return auxiliar;
						}
					}
					
					if((differenceBetweenBack < differenceBetweenMiddle 
							&& differenceBetweenBack < differenceBetweenFront) 
							|| differenceBetweenBack == differenceBetweenMiddle ) {
						
						Node auxiliar = this._back.Previous();
						
						for(Integer i = this._size-2; i > realIndex;i--)
							auxiliar = auxiliar.Previous();
						
						return auxiliar;
					}
				}
			}
		}
		
		return null;
	}
	
	//Public constructors
	
	/**Creates a new empty list with initial values.*/
	public List() { Init();}

	/**Creates a new list from an array of 'T' elements depending of the side to insert into the list.*/
	public List(T[] array, Side side) {
		
		this.Init();
		
		switch (side){
		case Front:
			for(T value:array)
				AddToFront(value);
			break;
		case Back:
			for(T value:array)
				AddToBack(value);
			break;
		}
	}
	
	
	/**Creates a new list from another list of 'T' elements.*/
	public List(List<T> list) {
		
		this.Init();
		
		Node middle = list.Middle();
		
		if(middle != null) {
			
			this.AddToBack(middle.Value());
			Node previous = middle.Previous();
			Node next = middle.Next();
			
			while(previous != null || next != null) {
				
				if(previous != null) {
					this.AddToFront(previous.Value());
					previous = previous.Previous();
				}
				if(next != null) {
					this.AddToBack(next.Value());
					next = next.Next();
				}
			}
		}
	}
	
	
	//Public Methods
	
	/**Returns true or false if the list is empty.*/
	public boolean Empty() {return this._empty;}
	
	/**Returns the number of elements into the list.*/
	public Integer Size() {return this._size;}
	
	/**Returns the first element into the list.*/
	public T First() { 
		if(this._front != null)
			return this._front.Value(); 
		else
			throw new NullPointerException("Error: The Value Is Null. Impossible To Return It.");
	}
	
	/**Returns the last element into the list.*/
	public T Last() { 
		if(this._back != null)
			return this._back.Value(); 
		else
			throw new NullPointerException("Error: The Value Is Null. Impossible To Return It.");
	}
	
	/**Returns the middle element into the list.*/
	public T Medium() { 
		if(this._middle != null)
			return this._middle.Value(); 
		else
			throw new NullPointerException("Error: The Value Is Null. Impossible To Return It.");
	}
	
	/**Returns the first node into the list.*/
	public Node Front() { return this._front; }
	
	/**Returns the last node into the list.*/
	public Node Back() { return this._back; }
	
	/**Returns the middle node into the list.*/
	public Node Middle() { return this._middle; }
	
	/**Returns the list's front element and removes it from the list. 
	 * @throws Throwable */
	public T Dequeue() throws Throwable{
		if(!this._empty) {
			T auxiliar = this._front.Value();
			PopFront();
			return auxiliar;
		}
		else 
			throw new NullPointerException("Error: The List Is Empty.");
	}
	
	/**Returns the list's back element and removes it. 
	 * @throws Throwable */
	public T UnStack() throws Throwable{
		if(!this._empty) {
			T auxiliar = this._back.Value();
			PopBack();
			return auxiliar;
		}
		else 
			throw new NullPointerException("Error: The List Is Empty.");
	}
	
	/**Adds a new element into the list from the back. Allows repetitions.*/
	public void AddToBack(T element) {
            ++this._size;
            if(this._empty) {
                    Node node = new Node(element);
                    this._front = this._back = this._middle = node;
                    this._empty = false;
            }
            else {
                Node node = new Node(element,this._back,null);
                this._back.Next(node);
                this._back = node;
                if(this._size % 2 != 0)
                        this._middle = this._middle.Next();
            }
	}
	
	/**Adds a new element into the list from the front. Allows repetitions.*/
	public void AddToFront(T element) {
		++this._size;
		if(this._empty) {
			Node node = new Node(element);
			this._front = this._back = this._middle = node;
			this._empty = false;
		}
		else {
			Node node = new Node(element,null,this._front);
			this._front.Previous(node);
			this._front = node;
			if(this._size % 2 == 0)
				this._middle = this._middle.Previous();
		}
	}
	
	/**Returns true or false if the element exist.*/
	public boolean Contains(T element) {
		
		switch(this._size) {
			case 0:
				return false;
			case 1:
					return this._front.Value().equals(element);
			case 2:
					return this._front.Value().equals(element) || this._back.Value().equals(element);
			default:{
				
				Node first = this._front;
				Node last = this._back;
				
				double middleIndex = ((double)this._size)/2;
				
				for(double i = 0; i < middleIndex;i++) {
					
					if(first.Value().equals(element))
						return true;
					
					if(last.Value().equals(element))
						return true;
					
					first = first.Next();
					last = last.Previous();
				}
				
				return false;
			}
		}
	}
	
	/**Adds a new element into the list depending on the side. Allows repetitions.*/
	public void Add(T element, Side side) {
		switch (side){
		case Front:
			AddToFront(element);
			break;
		case Back:
			AddToBack(element);
			break;
		}
	}
	
	/**Adds a new element into the list from the front. Does not allow repetitions.*/
	public void InsertToFront(T element) {
		if(!Contains(element))
			AddToFront(element);
	}
	
	/**Adds a new element into the list from the end. Does not allow repetitions.*/
	public void InsertToBack(T element) {
		if(!Contains(element))
			AddToBack(element);
	}
	
	/**Adds a new element into the list from the given side. Does not allow repetitions.*/
	public void Insert(T element, Side side) {
		if(!Contains(element))
			Add(element,side);
	}
	
	/**Gets the element from a given index.*/
	public T Get(Integer index) {
		
		if(!this._empty){
			
			Integer realIndex = index % this._size;
			
			if(realIndex == 0) 
				return this._front.Value();
			
			else if(realIndex == this._size-1) 
				return this._back.Value();
			
			else { 
				Integer middleIndex = this._size/2;
				
				if(this._size % 2 != 0) { 
					Integer differenceBetweenFront = Math.abs(realIndex-1);
					Integer differenceBetweenBack = Math.abs(realIndex-(this._size-2));
					Integer differenceBetweenMiddle = Math.abs(realIndex-middleIndex);
					
					if( (differenceBetweenFront < differenceBetweenBack 
							&& differenceBetweenFront < differenceBetweenMiddle ) 
							|| differenceBetweenFront == differenceBetweenMiddle) {
						
						Node auxiliar = this._front.Next();
						
						for(Integer i = 1; i < realIndex;i++)
							auxiliar = auxiliar.Next();
						
						return auxiliar.Value();
						
					}
					else if(differenceBetweenMiddle < differenceBetweenBack 
							&& differenceBetweenMiddle < differenceBetweenFront) {
						
						if(realIndex > middleIndex) {
							Node auxiliar = this._middle.Next();
							
							for(Integer i = middleIndex+1; i < realIndex;i++)
								auxiliar = auxiliar.Next();
							
							return auxiliar.Value();
						}
						else if(realIndex == middleIndex)
							return this._middle.Value();
						
						else {
							Node auxiliar = this._middle.Previous();
							
							for(Integer i = middleIndex-1; i > realIndex;i--)
								auxiliar = auxiliar.Previous();
							
							return auxiliar.Value();
						}
					}
					else{
					
						Node auxiliar = this._back.Previous();
						
						for(Integer i = this._size-2; i > realIndex;i--)
							auxiliar = auxiliar.Previous();
						
						return auxiliar.Value();
					}

				}
				else { 
					Integer differenceBetweenFront = Math.abs(realIndex-1);
					Integer differenceBetweenBack = Math.abs(realIndex-(this._size-2));
					Integer differenceBetweenMiddle = Math.abs(realIndex-(middleIndex-1));
	
					if( (differenceBetweenFront < differenceBetweenBack 
							&& differenceBetweenFront < differenceBetweenMiddle ) 
							|| differenceBetweenFront == differenceBetweenMiddle) {

						Node auxiliar = this._front.Next();
						
						for(Integer i = 1; i < realIndex;i++)
							auxiliar = auxiliar.Next();
						
						return auxiliar.Value();
						
					}
					
					else if(differenceBetweenMiddle < differenceBetweenBack 
							&& differenceBetweenMiddle < differenceBetweenFront) {

						if(realIndex > (middleIndex-1)) {
							
							Node auxiliar = this._middle.Next();
							
							for(Integer i = middleIndex; i < realIndex;i++)
								auxiliar = auxiliar.Next();
							
							return auxiliar.Value();
						}
						else if(realIndex == (middleIndex-1))
							return this._middle.Value();
						
						else {
							Node auxiliar = this._middle.Previous();
							
							for(Integer i = middleIndex-2; i > realIndex;i--)
								auxiliar = auxiliar.Previous();
							
							return auxiliar.Value();
						}
						
					}
					else{
						
						Node auxiliar = this._back.Previous();
						
						for(Integer i = this._size-2; i > realIndex;i--)
							auxiliar = auxiliar.Previous();
						
						return auxiliar.Value();
					}
				}
				
			}
		}

		else
			throw new NullPointerException("Error: The List Is Empty.");
	}

	/**Replaces an element for another one. Allows repetitions.*/
	public void Replace(T elementToReplace,T replacement){
		
		if(!this._empty) {
			Node node = GetNode(elementToReplace);
			
			if(node != null) 
				node.Value(replacement);
		}
	}
	
	/**Replaces every 'elementToReplace' for the 'replacement' . Allows repetitions.*/
	public void Replacing(T elementToReplace, T replacement) {
		if(!this._empty) {
			Node node = this._front;
			
			while(node != null) {
				
				if(node.Value().equals(elementToReplace))
					node.Value(replacement);
				
				node = node.Next();
			}
		}
	}

	/**Replaces an element positioned in a given position. Allows repetitions.*/
	public void Replace(Integer position,T replacement){
		
		if(!this._empty) {
			Node node = GetNode(position);
	
			if(node != null) 
				node.Value(replacement);
		}
		
	}
	
	/**Replaces an element for another one. Does not allow repetitions.*/
	public void SuperSede(T elementToReplace, T replacement) {
		if(!this._empty) {
			
			if(!Contains(replacement)) 
				Replace(elementToReplace,replacement);
		}
	}
	
	/**Replaces an element positioned in a given position. Does not allow repetitions.*/
	public void SuperSede(Integer position,T replacement) {
		
		if(!this._empty) {
			
			if(!Contains(replacement)) 
				Replace(position,replacement);
		}
	}

	/**Swaps the element located in the first position to the second position, and the element located in second position to the first position.*/
	public void Swap(Integer firstPosition, Integer secondPosition){
		if(!this._empty){

			if(firstPosition != secondPosition){
				
				Node first = GetNode(firstPosition);
				Node second = GetNode(secondPosition);

				T auxiliar = second.Value();
				second.Value(first.Value());
				first.Value(auxiliar);
				
			}
		}
	}
	
	/**Swaps both elements between themselves.
	 * @throws Throwable */
	public void Swap(T firstElement, T secondElement) throws Throwable{
		if(!this._empty){
			
			Pair<Node,Node> nodes = GetNodes(firstElement, secondElement);
			
			if(nodes.First() != null 
					&& nodes.Second() != null) {
				
				T auxiliar = nodes.First().Value();
				nodes.First().Value(nodes.Second().Value());
				nodes.Second().Value(auxiliar);
				
			}
			
			nodes.finalize();
			nodes = null;
		}
		
	}
	
	/**Returns the position where the given element is located on the list. If the element does not exist, it will return the value of -1. */
	public Integer GetPositionAt(T element){
		Integer frontCounter = 0;
		Integer backCounter = this._size-1;
		Node first = this._front;
		Node last = this._back;
		
		double middleIndex = ((double)this._size)/2;
		
		for(double i = 0; i < middleIndex;i++) {
			
			if(first.Value().equals(element))
				return frontCounter;
			
			if(last.Value().equals(element))
				return backCounter;
			
			frontCounter++;
			backCounter--;
			
			first = first.Next();
			last = last.Previous();
		}
		return -1;
	}
	
	/**Deletes the first element from the list.*/
	public void PopFront() {
		if(!this._empty){
			Node erasedNode = this._front;
			if(this._size % 2 == 0) 
				this._middle = this._middle.Next();
			this._front = this._front.Next();
			
		    if(this._front != null)
				this._front.Previous(null);
		    
			--this._size;
			
			if(this._size == 0)
				this._empty = true;
			
			erasedNode = null;
		}
	}
	
	/**Deletes the last element from the list.*/
	public void PopBack() {
		if(!this._empty){
			Node erasedNode = this._back;
			if(this._size % 2 != 0) 
				this._middle = this._middle.Previous();

			this._back = this._back.Previous();
			
		    if(this._back != null)
				this._back.Next(null);
		    
			--this._size;
			
			if(this._size == 0)
				this._empty = true;
			
			erasedNode = null;
		}
		
	}
	
	/**Adds elements from another list to the end of this. Allows repetitions.*/
	public void JoinToBack(List<T> list){
		
		Node auxiliar = list.Front();
		
		while(auxiliar != null) {
			this.AddToBack(auxiliar.Value());
			auxiliar = auxiliar.Next();
		}
	}
	
	/**Adds elements from another list to the front of this. Allows repetitions.*/
	public void JoinToFront(List<T> list){
		
		Node auxiliar = list.Front();
		
		while(auxiliar != null) {
			this.AddToFront(auxiliar.Value());
			auxiliar = auxiliar.Next();
		}
	}
	
	
	
	/**Adds elements from another list to the front of this. Does not allow repetitions.*/
	public void ConcatenateToFront(List<T> list){
		Node auxiliar = list.Front();
		
		while(auxiliar != null) {
			this.InsertToFront(auxiliar.Value());
			auxiliar = auxiliar.Next();
		}
	}
	
	/**Adds elements from another list to the end of this. Does not allow repetitions.*/
	public void ConcatenateToBack(List<T> list){
		Node auxiliar = list.Front();
		
		while(auxiliar != null) {
			this.InsertToBack(auxiliar.Value());
			auxiliar = auxiliar.Next();
		}
	}
	
	/**Reverses the list.*/
	public void Reverse() {
		
		if(!this._empty) {

			Node first = this._front;
			Node last = this._back;
			
			double middleIndex = ((double)this._size)/2;
			
			for(double i = 0; i < middleIndex;i++) {
				
				T firstvalue = first.Value();
				first.Value(last.Value());
				last.Value(firstvalue);
				
				first = first.Next();
				last = last.Previous();
			}
		}
	}
	
	/**Clears all the elements from the list. */
	public void Clear() {
		if(!this._empty){
                    while(!this._empty) {
                            PopFront();
                            PopBack();
                    }
                    this._size = 0;
                    this._empty = true;
                    this._back = this._middle  = this._front = null;
		}
	}
	
	/**Inserts a new element next to the given node. Allows repetitions. */
	public void InsertNextTo(Node node, T element) {
		if(!this._empty){
			
			if(node != null) {
				if(node.Value().equals(this._back.Value())) 
					AddToBack(element);
				else{
					
					Node previousNode = node;
					Node nextNode = previousNode.Next();
					Node newNode = new Node(element,previousNode,nextNode);
					
					previousNode.Next(newNode);
					nextNode.Previous(newNode);
					
					if(this._size % 2 == 0)
						this._middle = this._middle.Next();

					this._size++;
				}
			}
		}
	}
	
	/**Inserts a new element previous to the given node. Allows repetitions. */
	public void InsertPreviousTo(Node node, T element) {
		if(!this._empty){
			
			if(node != null) {
				if(node.Value().equals(this._front.Value())) 
					AddToFront(element);
				else {
					Node previousNode = node.Previous();
					Node nextNode = node;
					Node newNode = new Node(element,previousNode,nextNode);
					
					previousNode.Next(newNode);
					nextNode.Previous(newNode);
					
					if(this._size % 2 != 0)
						this._middle = this._middle.Previous();
				
					this._size++;
				}
			}
		}
	}
	
	/**Inserts a new element into a given position. Allows repetitions. */
	public void InsertAt(Integer position, T element){
		if(!this._empty){
			position = position % this._size;
			if(position == 0) AddToFront(element);
			else if(position == this._size-1) AddToBack(element);
			else{
				
				Integer middleIndex = (this._size+1)/2;
				
				if(position != middleIndex) {
					Node previousNode = GetNode(position-1);
					Node nextNode = previousNode.Next();
					Node newNode = new Node(element,previousNode,nextNode);
					
					previousNode.Next(newNode);
					nextNode.Previous(newNode);
					
					if(this._size % 2 != 0)
						this._middle = this._middle.Previous();
				}
				else {
					Node previousNode = GetNode(position-1);
					Node nextNode = previousNode.Next();
					Node newNode = new Node(element,previousNode,nextNode);
					
					previousNode.Next(newNode);
					nextNode.Previous(newNode);
					
					this._middle = newNode;
				}
				
				this._size++;
			}
		}
	}
	
	/**Inserts a new element into a given position. Does not allow repetitions. */
	public void IntroduceAt(Integer index, T element){
		
		if(!this._empty) {

			if(!Contains(element))
				InsertAt(index,element);
		}
	}
	
	/**Removes an element from a given position.
	 * @throws Throwable */
	public void EraseAt(Integer position) throws Throwable{
		if(!this._empty){
			
			Integer realPosition = position % this._size;
			
			if(realPosition == 0) 
				PopFront();
			
			else if(realPosition == (this._size -1) )
				PopBack();
			
			else{
				
				Node previousNode = GetNode(position-1);
				Node erasedNode = previousNode.Next();
				boolean isMiddle = erasedNode.Value().equals(this._middle.Value());
				Node nextNode = erasedNode.Next();
				
				previousNode.Next(nextNode);
				nextNode.Previous(previousNode);
				
				erasedNode.finalize();
				erasedNode = null;
		        
		        this._size--;
		        
		        if(!isMiddle) {
			        if(this._size % 2 != 0)
						this._middle = this._middle.Next();
		        }
		        else 
		        	this._middle = (this._size % 2 == 0) ? previousNode : nextNode;
			}
		}
	}
	
	/**Removes the given element from the list.
	 * @throws Throwable */
	public void Remove(T element) throws Throwable {
		if(!this._empty) {
			
			if(this._front.Value().equals(element))
				PopFront();
			
			else if(this._back.Value().equals(element))
				PopBack();
			
			else {
				
				Node erasedNode = GetNode(element);
				
				if(erasedNode != null) {
					Node previousNode = erasedNode.Previous();
					boolean isMiddle = erasedNode.Value().equals(this._middle.Value());
					Node nextNode = erasedNode.Next();
					
					previousNode.Next(nextNode);
					nextNode.Previous(previousNode);
					
					erasedNode.finalize();
					erasedNode = null;
			        
			        this._size--;
			        
			        if(!isMiddle) {
				        if(this._size % 2 != 0)
							this._middle = this._middle.Next();
			        }
			        else 
			        	this._middle = (this._size % 2 == 0) ? previousNode : nextNode;

				}
			}
		}
	}
	
	
	//Override methods
	
	@Override
	public String toString(){
		
		String newString = new String();
		
		Node auxiliar = this._front;
		
		while(auxiliar != null){
			newString += "["+auxiliar.Value()+"]\n";
			auxiliar = auxiliar.Next();
		}
		return newString;
	}
	
	@Override 
	public boolean equals(Object object) {
		if (object == this) 
            return true; 

        if (!(object instanceof List))
            return false; 
 
		@SuppressWarnings("unchecked")
		List<T> objectList = (List<T>) object; 
          
        if(this._size == objectList._size) {
        	
        	Node thisFront = this._front;
        	Node objectFront = objectList._front;
        	
        	while(thisFront != null) {
        		
        		if(!thisFront.Value().equals(objectFront.Value()))
        			return false;
        		
        		thisFront = thisFront.Next();
        		objectFront = objectFront.Next();
        	}
        	
        	return true;
        }
        
        return false;
	}
	
	@Override
	protected void finalize() throws Throwable { 
		this.Clear(); 
		this._size = null; 
		System.gc();
	}
	
}