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


/**Pair class of 'A' and 'B' elements.*/
public class Pair<A,B>{
	
	//Protected attributes
	
	/**The first pair 'A' element.*/
	protected A _firstElement; 
	/**The second pair 'B' element.*/
	protected B _secondElement; 
	
	//Public constructors
	
	/**Creates a new pair from a first type 'A' element and a second type 'B' element.*/
	public Pair(A firstElement, B secondElement) {
		this._firstElement = firstElement;
		this._secondElement = secondElement;
	}
	
	/**Creates a new pair from another one.*/
	public Pair(Pair<A,B> anotherPair) {
		this._firstElement = anotherPair._firstElement;
		this._secondElement = anotherPair._secondElement;
	}
	
	//Public methods
	
	/**Sets a new first 'A' element's value.*/
	public void First(A element) {this._firstElement = element;}
	
	/**Returns the first 'A' element's value.*/
	public A First() {
		if(this._firstElement != null)
			return this._firstElement;
		else
			throw new NullPointerException("Error: The Value Is Null. Impossible To Return It.");
	}
	
	/**Sets new second 'B' element's value.*/
	public void Second(B element) {this._secondElement = element;}
	
	/**Returns the second 'B' element's value.*/
	public B Second() {
		if(this._secondElement != null)
			return this._secondElement;
		else
			throw new NullPointerException("Error: The Value Is Null. Impossible To Return It.");
	}
	
	/**Returns the first 'A' element's value name type.*/
	public String FirstType() { return this._firstElement.getClass().getTypeName(); }
	
	/**Returns the second 'B' element's value name type.*/
	public String SecondType() { return this._secondElement.getClass().getTypeName(); }
	
	//Override methods
	
	@Override
	public boolean equals(Object object){
		
        if(object == this) 
            return true; 
        if(!(object instanceof Pair))
            return false; 
        
        @SuppressWarnings("unchecked")
		Pair<A,B> objectPair = (Pair<A,B>) object; 
        
        return this._firstElement.equals(objectPair.First()) &&
        		this._secondElement.equals(objectPair.Second());
	}
	
	@Override
	public String toString(){ return "("+this._firstElement + ",  "+this._secondElement +")";}
	
	@Override
	protected void finalize() throws Throwable {
		this._firstElement =  null;
		this._secondElement = null;
		System.gc();
	}
}