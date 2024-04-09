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
public /**Double List Class*/
class DoubleList<M,N>{
	
	//Private attributes
	
	/**The pair list who is going to work as a double list.*/
	List<Pair<M,N>> _doubleList;
	
	//Public constructors
	
	/**Creates a new empty double list.*/
	public DoubleList() { this._doubleList = new List<Pair<M,N>>();}
	
	/**Creates a new double list from another one.*/
	public DoubleList(DoubleList<M,N> DoubleList) {
		this._doubleList = new List<Pair<M,N>>();
		
		List<Pair<M,N>>.Node middle = DoubleList.Middle();
		
		if(middle != null) {
			
			this.AddToBack(middle.Value());
			List<Pair<M,N>>.Node previous = middle.Previous();
			List<Pair<M,N>>.Node next = middle.Next();
			
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
	
	/**Returns true or false if the double list is empty.*/
	public boolean Empty() {return this._doubleList.Empty();}
	
	/**Returns the number of elements into the double list.*/
	public Integer Size() {return this._doubleList.Size();}
	
	/**Returns the first pair element into the double list.*/
	public Pair<M,N> First() {  return this._doubleList.First();}
	
	/**Returns the last pair element into the double list.*/
	public Pair<M,N> Last() {  return this._doubleList.Last();}

	/**Returns the middle pair element into the double list.*/
	public Pair<M,N> Medium() { return this._doubleList.Medium();}
	
	/**Returns the first node into the double list.*/
	public List<Pair<M,N>>.Node Front() { return this._doubleList.Front(); }
	
	/**Returns the last node into the double list.*/
	public List<Pair<M,N>>.Node Back() { return this._doubleList.Back(); }
	
	/**Returns the middle node into the double list.*/
	public List<Pair<M,N>>.Node Middle() { return this._doubleList.Middle(); }
	
	/**Returns the double list's front element and removes it. 
	 * @throws Throwable */
	public Pair<M,N> Dequeue() throws Throwable{
		if(!this._doubleList.Empty()) {
			Pair<M,N> auxiliar = First();
			PopFront();
			return auxiliar;
		}
		else 
			throw new NullPointerException("Error: The Double List Is Empty.");
	}
	
	/**Returns the double list's back element and removes it. 
	 * @throws Throwable */
	public Pair<M,N> UnStack() throws Throwable{
		if(!this._doubleList.Empty()) {
			Pair<M,N> auxiliar = Last();
			PopBack();
			return auxiliar;
		}
		else 
			throw new NullPointerException("Error: The Double List Is Empty.");
	}
	
	/**Adds a new pair element into the double list from the back side. Allows repetitions.*/
	public void AddToBack(Pair<M,N> element) { this._doubleList.AddToBack(element);}
	
	/**Adds new 'M' and 'N' elements into the double list from the back side. Allows repetitions.
	 * @throws Throwable */
	public void AddToBack(M firstElement, N secondElement) { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.AddToBack(auxiliar);
	
	}
	
	/**Adds a new element into the double list from the front side. Allows repetitions.*/
	public void AddToFront(Pair<M,N> element) { this._doubleList.AddToFront(element);}
	
	/**Adds new 'M' and 'N' elements into the double list from the front side. Allows repetitions.
	 * @throws Throwable */
	public void AddToFront(M firstElement, N secondElement) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.AddToFront(auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
        
        public Pair<M,N> GetAt(Integer i){
            return this._doubleList.Get(i);
        }
	
	/**Returns true or false if the pair element exist into the double list.*/
	public boolean Contains(Pair<M,N> element) { return this._doubleList.Contains(element);}
	
	/**Returns true or false if the 'M' element exist in a pair element into the double list.*/
	public boolean ContainsFirst(M element) {
		switch(this._doubleList.Size()) {
			case 0:
				return false;
			case 1:
					return this._doubleList.First().First().equals(element);
			case 2:
					return this._doubleList.First().First().equals(element) || this._doubleList.Last().First().equals(element);
			default:{
				
				List<Pair<M,N>>.Node first = this._doubleList.Front();
				List<Pair<M,N>>.Node last = this._doubleList.Back();
				
				double middleIndex = ((double)this._doubleList.Size())/2;
				
				for(double i = 0; i < middleIndex;i++) {
					
					if(first.Value().First().equals(element))
						return true;
					
					if(last.Value().First().equals(element))
						return true;
					
					first = first.Next();
					last = last.Previous();
				}
				
				return false;
			}
		}
	}
        
        
        /**Returns the Node where the first element is.*/
	public List<Pair<M,N>>.Node GetNodeFromFirst(M element) {
                List<Pair<M,N>>.Node first = this._doubleList.Front();
                List<Pair<M,N>>.Node last = this._doubleList.Back();

                double middleIndex = ((double)this._doubleList.Size())/2;

                for(double i = 0; i < middleIndex;i++) {

                        if(first.Value().First().equals(element))
                            return first;

                        if(last.Value().First().equals(element))
                            return last;

                        first = first.Next();
                        last = last.Previous();
                }

                return null;
		
	}
	
	/**Returns true or false if the 'N' element exist in a pair element into the double list.*/
	public boolean ContainsSecond(N element) {
		switch(this._doubleList.Size()) {
			case 0:
				return false;
			case 1:
					return this._doubleList.First().Second().equals(element);
			case 2:
					return this._doubleList.First().Second().equals(element) || this._doubleList.Last().Second().equals(element);
			default:{
				
				List<Pair<M,N>>.Node first = this._doubleList.Front();
				List<Pair<M,N>>.Node last = this._doubleList.Back();
				
				double middleIndex = ((double)this._doubleList.Size())/2;
				
				for(double i = 0; i < middleIndex;i++) {
					
					if(first.Value().Second().equals(element))
						return true;
					
					if(last.Value().Second().equals(element))
						return true;
					
					first = first.Next();
					last = last.Previous();
				}
				
				return false;
			}
		}
	}
	
	/**Returns true or false if the 'M' and 'N' elements exist into the double list.
	 * @throws Throwable */
	public boolean Contains(M firstElement, N secondElement) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		boolean contains = this._doubleList.Contains(auxiliar);
		auxiliar.finalize();
		auxiliar = null;
		return contains;
	}
	
	/**Adds a new pair element into the double list depending of the side. Allows repetitions.*/
	public void Add(Pair<M,N> element, Side side) { this._doubleList.Add(element, side);}
	
	/**Adds 'M' and 'N' elements into the double list depending of the side. Allows repetitions.
	 * @throws Throwable */
	public void Add(M firstElement, N secondElement, Side side) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.Add(auxiliar, side);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Adds a new pair element into the double list from the front. Does not allow repetitions.*/
	public void InsertToFront(Pair<M,N> element) { this._doubleList.InsertToFront(element);}
	
	/**Adds new 'M' and 'N' elements into the double list from the front. Does not allow repetitions.
	 * @throws Throwable */
	public void InsertToFront(M firstElement, N secondElement) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.InsertToFront(auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Adds a new pair element into the double list from the end. Does not allow repetitions.*/
	public void InsertToBack(Pair<M,N> element) { this._doubleList.InsertToBack(element);}
	
	/**Adds new 'M' and 'N' elements into the double list from the end. Does not allow repetitions.
	 * @throws Throwable */
	public void InsertToBack(M firstElement, N secondElement) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.InsertToBack(auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Adds a new pair element into the double list from the given side. Does not allow repetitions.*/
	public void Insert(Pair<M,N> element, Side side) { this._doubleList.Insert(element, side);}
	
	/**Adds new 'M' and 'N' elements into the double list from the given side. Does not allow repetitions.
	 * @throws Throwable */
	public void Insert(M firstElement, N secondElement, Side side) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.Insert(auxiliar, side);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Replaces a pair element for another one. Allows repetitions.*/
	public void Replace(Pair<M,N> elementToReplace,Pair<M,N> replacement){ this._doubleList.Replace(elementToReplace,replacement);}

	/**Replaces 'M' and 'N' elements for another ones. Allows repetitions.
	 * @throws Throwable */
	public void Replace(M firstElementToReplace, N secondElementToReplace,Pair<M,N> replacement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElementToReplace,secondElementToReplace);
		this._doubleList.Replace(auxiliar,replacement);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Replaces 'M' and 'N' elements for another ones. Allows repetitions.
	 * @throws Throwable */
	public void Replace(M firstElementToReplace, N secondElementToReplace,M firstReplacement, N secondReplacement) throws Throwable{ 
		Pair<M,N> firstAuxiliar = new Pair<M,N>(firstElementToReplace,secondElementToReplace);
		Pair<M,N> secondAuxiliar = new Pair<M,N>(firstReplacement,secondReplacement);
		this._doubleList.Replace(firstAuxiliar,secondAuxiliar);
		firstAuxiliar.finalize();
		firstAuxiliar = null;
		secondAuxiliar.finalize();
		secondAuxiliar = null;
	}
	
	/**Replaces 'M' and 'N' elements for a pair element to replace. Allows repetitions.
	 * @throws Throwable */
	public void Replace(Pair<M,N> elementToReplace, M firstReplacement, N secondReplacement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstReplacement,secondReplacement);
		this._doubleList.Replace(elementToReplace,auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Replaces every pair elementToReplace for the pair replacement. Allows repetitions.*/
	public void Replacing(Pair<M,N> elementToReplace, Pair<M,N> replacement) { this._doubleList.Replacing(elementToReplace,replacement);}
	
	/**Replaces every pair elementToReplace for the 'M' and 'N' replacements. Allows repetitions.
	 * @throws Throwable */
	public void Replacing(Pair<M,N> elementToReplace, M firstReplacement, N secondReplacement) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstReplacement,secondReplacement);
		this._doubleList.Replacing(elementToReplace,auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Replaces every 'M' and 'N' elementsToReplace for the M' and 'N' replacements. Allows repetitions.
	 * @throws Throwable */
	public void Replacing(M firstElementToReplace, N secondElementToReplace, M firstReplacement, N secondReplacement) throws Throwable { 
		Pair<M,N> firstAuxiliar = new Pair<M,N>(firstElementToReplace,secondElementToReplace);
		Pair<M,N> secondAuxiliar = new Pair<M,N>(firstReplacement,secondReplacement);
		this._doubleList.Replacing(firstAuxiliar,secondAuxiliar);
		firstAuxiliar.finalize();
		firstAuxiliar = null;
		secondAuxiliar.finalize();
		secondAuxiliar = null;
	}
	
	/**Replaces every M' and 'N' elementsToReplace for the pair replacement. Allows repetitions.
	 * @throws Throwable */
	public void Replacing(M firstElementToReplace, N secondElementToReplace, Pair<M,N> replacement) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElementToReplace,secondElementToReplace);
		this._doubleList.Replacing(auxiliar,replacement);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Replaces a pair element positioned in a given position. Allows repetitions.*/
	public void Replace(Integer position,Pair<M,N> replacement){ this._doubleList.Replace(position, replacement); }
	
	/**Replaces 'M' and 'N' elements positioned in a given position. Allows repetitions.
	 * @throws Throwable */
	public void Replace(Integer position,M firstReplacement, N secondReplacement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstReplacement,secondReplacement);
		this._doubleList.Replace(position, auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Replaces a pair element for another one. Does not allow repetitions.*/
	public void SuperSede(Pair<M,N> elementToReplace, Pair<M,N> replacement) { this._doubleList.SuperSede(elementToReplace, replacement);}
	
	/**Replaces 'M' and 'N' elements for another ones. Does not allow repetitions.
	 * @throws Throwable */
	public void SuperSede(M firstElementToReplace, N secondElementToReplace,Pair<M,N> replacement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElementToReplace,secondElementToReplace);
		this._doubleList.SuperSede(auxiliar,replacement);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Replaces 'M' and 'N' elements for another ones. Does not allow repetitions.
	 * @throws Throwable */
	public void SuperSede(M firstElementToReplace, N secondElementToReplace,M firstReplacement, N secondReplacement) throws Throwable{ 
		Pair<M,N> firstAuxiliar = new Pair<M,N>(firstElementToReplace,secondElementToReplace);
		Pair<M,N> secondAuxiliar = new Pair<M,N>(firstReplacement,secondReplacement);
		this._doubleList.SuperSede(firstAuxiliar,secondAuxiliar);
		firstAuxiliar.finalize();
		firstAuxiliar = null;
		secondAuxiliar.finalize();
		secondAuxiliar = null;
	}
	
	/**Replaces 'M' and 'N' elements for a pair element to replace. Does not allow repetitions.
	 * @throws Throwable */
	public void SuperSede(Pair<M,N> elementToReplace, M firstReplacement, N secondReplacement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstReplacement,secondReplacement);
		this._doubleList.SuperSede(elementToReplace,auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Replaces a pair element positioned in a given position. Does not allow repetitions.*/
	public void SuperSede(Integer position, Pair<M,N> replacement) { this._doubleList.SuperSede(position, replacement);}
	
	/**Replaces 'M' and 'N' elements positioned in a given position. Does not allow repetitions.
	 * @throws Throwable */
	public void SuperSede(Integer position,M firstReplacement, N secondReplacement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstReplacement,secondReplacement);
		this._doubleList.SuperSede(position, auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}

	/**Swaps the pair element located in the first position to the second position, and the element located in the second position to the first position.*/
	public void Swap(Integer firstPosition, Integer secondPosition){this._doubleList.Swap(firstPosition, secondPosition);}
	
	/**Swaps both pair elements between themselves. 
	 * @throws Throwable */
	public void Swap(Pair<M,N> firstElement, Pair<M,N> secondElement) throws Throwable{ this._doubleList.Swap(firstElement, secondElement);}
	
	/**Swaps the pair element position to the first and second element. 
	 * @throws Throwable */
	public void Swap(Pair<M,N> pairElement, M firstElement, N secondElement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.Swap(pairElement, auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Swaps the first and second element to the pair element position. 
	 * @throws Throwable */
	public void Swap(M firstElement, N secondElement, Pair<M,N> pairElement) throws Throwable{
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.Swap(auxiliar, pairElement);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Swaps the first and second element to the third and fourth element position. 
	 * @throws Throwable */
	public void Swap(M firstElement, N secondElement, M thirdElement, N fourthElement) throws Throwable{ 
		Pair<M,N> firstAuxiliar = new Pair<M,N>(firstElement,secondElement);
		Pair<M,N> secondAuxiliar = new Pair<M,N>(thirdElement,fourthElement);
		this._doubleList.Swap(firstAuxiliar, secondAuxiliar);
		firstAuxiliar.finalize();
		firstAuxiliar = null;
		secondAuxiliar.finalize();
		secondAuxiliar = null;
	}
	
	/**Returns the position where the given pair element is located on the double list. If the pair element does not exist, it will return the value of -1. */
	public Integer GetPositionAt(Pair<M,N> element){ return this._doubleList.GetPositionAt(element);}
	
	/**Returns the position where the given 'M' and 'N' elements are located on the double list. If the pair element does not exist, it will return the value of -1. 
	 * @throws Throwable */
	public Integer GetPositionAt(M firstElement, N secondElement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		Integer position = this._doubleList.GetPositionAt(auxiliar);
		auxiliar.finalize();
		auxiliar = null;
		return position;
	}
	
	/**Deletes the first element from the double list.
	 * @throws Throwable */
	public void PopFront() throws Throwable{ this._doubleList.PopFront();}
	
	/**Deletes the last element from the double list. 
	 * @throws Throwable */
	public void PopBack() throws Throwable{ this._doubleList.PopBack();}
	
	/**Adds elements from another double list to the end of this. Allows repetitions. */
	public void JoinToBack(DoubleList<M,N> DoubleList){
		List<Pair<M,N>>.Node auxiliar = DoubleList.Front();
		while(auxiliar != null) {
			this.AddToBack(auxiliar.Value());
			auxiliar = auxiliar.Next();
		}
	}
	
	/**Adds elements from another double list to the front of this. Allows repetitions. */
	public void JoinToFront(DoubleList<M,N> DoubleList){
		List<Pair<M,N>>.Node auxiliar = DoubleList.Front();
		while(auxiliar != null) {
			this.AddToFront(auxiliar.Value());
			auxiliar = auxiliar.Next();
		}
	}
	
	/**Adds elements from another double list to the front of this. Does not allow repetitions. */
	public void ConcatenateToFront(DoubleList<M,N> DoubleList){
		List<Pair<M,N>>.Node auxiliar = DoubleList.Front();
		while(auxiliar != null) {
			this.InsertToFront(auxiliar.Value());
			auxiliar = auxiliar.Next();
		}
	}
	
	/**Adds elements from another double list to the end of this. Does not allow repetitions. */
	public void ConcatenateToBack(DoubleList<M,N> DoubleList){
		List<Pair<M,N>>.Node auxiliar = DoubleList.Front();
		while(auxiliar != null) {
			this.InsertToBack(auxiliar.Value());
			auxiliar = auxiliar.Next();
		}
	}

	/**Reverses the double list*/
	public void Reverse() {
		
		if(!this._doubleList.Empty()) {

			List<Pair<M,N>>.Node  first = this._doubleList.Front();
			List<Pair<M,N>>.Node last = this._doubleList.Back();
			
			double middleIndex = ((double)this._doubleList.Size())/2;
			
			for(double i = 0; i < middleIndex;i++) {
				
				Pair<M,N> firstvalue = first.Value();
				first.Value(last.Value());
				last.Value(firstvalue);
				
				first = first.Next();
				last = last.Previous();
			}
		}
	}
	
	/**Clears all the elements from the double list. 
	 * @throws Throwable */
	public void Clear() throws Throwable{ this._doubleList.Clear();}
	
	/**Inserts a new pair element next to the given node. Allows repetitions. */
	public void InsertNextTo(List<Pair<M,N>>.Node node, Pair<M,N> element) { this._doubleList.InsertNextTo(node, element);}
	
	/**Inserts new 'M' and 'N' elements next to the given node. Allows repetitions. 
	 * @throws Throwable */
	public void InsertNextTo(List<Pair<M,N>>.Node node, M firstElement, N secondElement) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.InsertNextTo(node, auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Inserts a new pair element previous to the given node. Allows repetitions. */
	public void InsertPreviousTo(List<Pair<M,N>>.Node node, Pair<M,N> element) { this._doubleList.InsertPreviousTo(node, element);}
	
	/**Inserts new 'M' and 'N' elements previous to the given node. Allows repetitions. 
	 * @throws Throwable */
	public void InsertPreviousTo(List<Pair<M,N>>.Node node, M firstElement, N secondElement) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.InsertPreviousTo(node,auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Inserts a new pair element into a given position. Allows repetitions. */
	public void InsertAt(Integer position, Pair<M,N> element){ this._doubleList.InsertAt(position, element);}
	
	/**Inserts new 'M' and 'N' elements into a given position. Allows repetitions. 
	 * @throws Throwable */
	public void InsertAt(Integer position, M firstElement, N secondElement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.InsertAt(position,auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Inserts a new pair element into a given position. Does not allow repetitions. */
	public void IntroduceAt(Integer index, Pair<M,N> element){ this._doubleList.IntroduceAt(index, element);}
	
	/**Inserts new 'M' and 'N' elements into a given position. Does not allow repetitions. 
	 * @throws Throwable */
	public void IntroduceAt(Integer index, M firstElement, N secondElement) throws Throwable{ 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.IntroduceAt(index, auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	/**Removes a pair element from a given position. 
	 * @throws Throwable */
	public void EraseAt(Integer position) throws Throwable{ this._doubleList.EraseAt(position);}
	
	/**Removes the pair element from the double list. 
	 * @throws Throwable */
	public void Remove(Pair<M,N> element) throws Throwable { this._doubleList.Remove(element);}
	
	/**Removes the 'M' and 'N' elements from the double list. 
	 * @throws Throwable */
	public void Remove(M firstElement, N secondElement) throws Throwable { 
		Pair<M,N> auxiliar = new Pair<M,N>(firstElement,secondElement);
		this._doubleList.Remove(auxiliar);
		auxiliar.finalize();
		auxiliar = null;
	}
	
	//Override methods
	
	@Override
	public String toString() { return _doubleList.toString();}
	
	@Override 
	public boolean equals(Object object) {
		if (object == this) 
            return true; 

        if (!(object instanceof DoubleList))
            return false; 
 
		@SuppressWarnings("unchecked")
		DoubleList<M,N> objectDoubleList = (DoubleList<M,N>) object; 
          
        if(this._doubleList.Size() == objectDoubleList.Size()) {
        	
        	List<Pair<M,N>>.Node thisFront = this._doubleList.Front();
        	List<Pair<M,N>>.Node objectFront = objectDoubleList.Front();
        	
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
	protected void finalize() throws Throwable { this._doubleList.finalize();}
}
