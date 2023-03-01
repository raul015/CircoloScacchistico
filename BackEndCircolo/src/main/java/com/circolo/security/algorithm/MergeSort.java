package com.circolo.security.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.circolo.security.iscrizione.Iscrizione;



 // Adattare il Marge Sort al codice... devo ordinare per punteggio
/*
 * Dove richiamo la funzione :
 * 
 *  MergeSort ms = new MergeSort(unsortedArray);
 *  
 *   ms.sortGivenArray();
 * 
 */
public class MergeSort {
	
	
private List<Iscrizione> inputArray;
    
    public List<Iscrizione> getSortedArray() {
        return inputArray;
    }
 
    public MergeSort(List<Iscrizione> inputArray){
        this.inputArray = inputArray;
    }
    
    public void sortGivenArray(){       
        divide(0, this.inputArray.size()-1);
    }
    
    public void divide(int startIndex,int endIndex){
        
        //Divide till you breakdown your list to single element
        if(startIndex<endIndex && (endIndex-startIndex)>=1){
            int mid = (endIndex + startIndex)/2;
            divide(startIndex, mid);
            divide(mid+1, endIndex);        
            
            //merging Sorted array produce above into one sorted array
            merger(startIndex,mid,endIndex);            
        }       
    }   
    
    public void merger(int startIndex,int midIndex,int endIndex){
        
        //Below is the mergedarray that will be sorted array Array[i-midIndex] , Array[(midIndex+1)-endIndex]
        List<Iscrizione> mergedSortedArray = new ArrayList<Iscrizione>();
        
        int leftIndex = startIndex;
        int rightIndex = midIndex+1;
        
        while(leftIndex<=midIndex && rightIndex<=endIndex){
        	
        	// Forse basta cambiare questa condizione per invertire l'ordine della lista... 
        	
        	//											<=
            if(inputArray.get(leftIndex).getPunteggio()	>=	inputArray.get(rightIndex).getPunteggio()){
            	
                mergedSortedArray.add(inputArray.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(inputArray.get(rightIndex));
                rightIndex++;
            }
        }       
        
        //Either of below while loop will execute
        while(leftIndex<=midIndex){
            mergedSortedArray.add(inputArray.get(leftIndex));
            leftIndex++;
        }
        
        while(rightIndex<=endIndex){
            mergedSortedArray.add(inputArray.get(rightIndex));
            rightIndex++;
        }
        
        int i = 0;
        int j = startIndex;
        //Setting sorted array to original one
        while(i<mergedSortedArray.size()){
            inputArray.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

}
