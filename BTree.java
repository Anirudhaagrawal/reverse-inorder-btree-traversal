/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adsproject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author vigilante
 */
public class BTree {

    /**
     * @param args the command line arguments
     */
    int count=0;
private int T;

public class bNode {
int n;
int key[] = new int[2*T-1];
bNode child[] = new bNode[2*T];
boolean leaf = true;



public int Find(int k){
        for(int i = 0; i < this.n; i++) {
            if (this.key[i] == k) {
                        return i;
                }
        }
        return -1;
};
}

private class PairNode {
bNode x;
int pos;
}

public BTree(int _T) {
        T = _T;
        root = new bNode();
        root.n = 0;
        root.leaf = true;
}

private bNode root;

private bNode Search (bNode x,int key) {
        int i = 0;
        if (x == null) return x;
        for (i = 0; i < x.n; i++) {
                if (key < x.key[i]) {
                        break;
                }
                if (key == x.key[i]) {
                        return x;
                }
        }
        if (x.leaf) {
                return null;
        } else {
                return Search(x.child[i],key);
        }
}

private void Split (bNode x, int pos, bNode y) {
        bNode z = new bNode();
        z.leaf = y.leaf;
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
                z.key[j] = y.key[j+T];
        }
        if (!y.leaf) {
                for (int j = 0; j < T; j++) {
                        z.child[j] = y.child[j+T];
                }
        }
        y.n = T-1;
        for (int j = x.n; j >= pos+1; j--) {
                x.child[j+1] = x.child[j];
        }
        x.child[pos+1] = z;

        for (int j = x.n-1; j >= pos; j--) {
                x.key[j+1] = x.key[j];
        }
        x.key[pos] = y.key[T-1];
        x.n = x.n + 1;
}

public void Insert (final int key) {
        bNode r = root;
        if (r.n == 2*T - 1 ) {
                bNode s = new bNode();
                root = s;
                s.leaf = false;
                s.n = 0;
                s.child[0] = r;
                Split(s,0,r);
                _Insert(s,key);
        } else {
                _Insert(r,key);
        }
}

final private void _Insert (bNode x, int k) {

        if (x.leaf) {
                int i = 0;
                for (i = x.n-1; i >= 0 && k < x.key[i]; i--) {
                        x.key[i+1] = x.key[i];
                }
                x.key[i+1] = k;
                x.n = x.n + 1;
        } else {
                int i = 0;
                for (i = x.n-1; i >= 0 && k < x.key[i]; i--) {};
                i++;
                bNode tmp = x.child[i];
                if (tmp.n == 2*T -1) {
                        Split(x,i,tmp);
                        if ( k > x.key[i]) {
                                i++;
                        }
                }
                _Insert(x.child[i], k);
        }

}

public void Show () {
        Show(root);
}

private void Show (bNode x) {
    
        assert (x == null);
       
        if (!x.leaf) {
                for (int i = 0; i <  x.n + 1; i++) {
                        Show(x.child[i]);
                }
      
        }
        else
        {
            if(count%2==0)
            {
                            System.out.print(x.leaf + " " + x.n + ":" );
                               for ( int i = 0; i < x.n; i++) {
                                    System.out.print((char)(x.key[i])+ " ");
                                    }
                                  System.out.println();
                              
                                   
            }
                  count++;
            
        }
          
       }



private void Remove (bNode x, int key) {
        int pos = x.Find(key);
        if (pos != -1) {
                if (x.leaf) {
                        int i = 0;
                        for (i = 0; i < x.n && x.key[i] != key; i++) {};
                        for (; i < x.n; i++) {
                                if (i != 2*T - 2) {
                                        x.key[i] = x.key[i+1];
                                }
                        }
                        x.n--;
                        return;
                }
                if (!x.leaf) {
                        //if (x.child[pos].n >= T){
                        bNode pred = x.child[pos];
                        int predKey = 0;
                        //System.out.println(pos);
                        if (pred.n >= T) {
                                //System.out.println("2a");
                                for (;;) {
                                        if (pred.leaf) {
                                                System.out.println(pred.n);
                                                predKey = pred.key[pred.n - 1];
                                                break;
                                        } else {
                                                pred = pred.child[pred.n];
                                        }
                                }
                                Remove (pred, predKey);
                                x.key[pos] = predKey;
                                return;
                        }
                        //System.out.println("2b");
                         bNode nextNode = x.child[pos+1];
                        if (nextNode.n >= T) {
                                int nextKey = nextNode.key[0];
                                if (!nextNode.leaf) {
                                        nextNode = nextNode.child[0];
                                        for (;;) {
                                                if (nextNode.leaf) {
                                                        nextKey = nextNode.key[nextNode.n-1];
                                                        break;
                                                } else {
                                                        nextNode = nextNode.child[nextNode.n];
                                                }
                                        }
                                }
                                Remove(nextNode, nextKey);
                                x.key[pos] = nextKey;
                                return;
                        }
                        //System.out.println("2v");
                        int temp = pred.n + 1;
                               
        pred.key[pred.n++] = x.key[pos];
                        for (int i = 0, j = pred.n; i < nextNode.n; i++) {
                                pred.key[j++] = nextNode.key[i];
                                pred.n++;
                        }
                        for (int i = 0; i < nextNode.n+1; i++) {
                                pred.child[temp++] = nextNode.child[i];
                        }

                        x.child[pos] = pred;
                        for (int i = pos; i < x.n; i++) {
                                if (i != 2*T - 2) {
                                        x.key[i] = x.key[i+1];
                                }
                        }
                        for (int i = pos+1; i < x.n+1; i++) {
                                if (i != 2*T - 1) {
                                        x.child[i] = x.child[i+1];
                                }
                        }
                        x.n--;
                        if (x.n == 0) {
                                if (x == root) {
                                        root = x.child[0];
                                }
                                x = x.child[0];
                        }
                        Remove(pred,key);
                        return;
                        //}
                }
        } else {
                for (pos = 0; pos < x.n; pos++) {
                        if (x.key[pos] > key) {
                                break;
                        }
                }
//			System.out.println(pos);
//			Show(x);
                bNode tmp = x.child[pos];
                if (tmp.n >= T) {
                        Remove (tmp,key);
                        return;
                }
//			System.out.println(pos + " " + T + " " + tmp.n);
                if (true) {
                        bNode nb = null;
                        int devider = -1;
                        if (pos != x.n && x.child[pos+1].n >= T) {
                                devider = x.key[pos];
                                nb = x.child[pos+1];
                                x.key[pos] = nb.key[0];
                                tmp.key[tmp.n++] = devider;
                                tmp.child[tmp.n] = nb.child[0];
                                for (int i = 1; i < nb.n; i++) {
                                        nb.key[i-1] = nb.key[i];
                                }
                                for (int i = 1; i <= nb.n; i++) {
                                        nb.child[i-1] = nb.child[i];
                                }
                                nb.n--;
                                Remove(tmp,key);
                                  return;
                        } else if (pos != 0 && x.child[pos-1].n >= T) {
                                devider = x.key[pos-1];
                                nb = x.child[pos-1];
                                x.key[pos-1] = nb.key[nb.n-1];
                                bNode child = nb.child[nb.n];
                                nb.n--;
                                for(int i = tmp.n; i > 0; i--) {
                                        tmp.key[i] = tmp.key[i-1];
                                }
                                tmp.key[0] = devider;
                                for(int i = tmp.n + 1; i > 0; i--) {
                                        tmp.child[i] = tmp.child[i-1];
                                }
                                tmp.child[0] = child;
                                tmp.n++;
//					Show(root);
                                Remove(tmp,key);
                                return;
                        } else {
//					devider = x.key[pos];
//					Show(x);
                                bNode lt = null;
                                bNode rt = null;
                                boolean last = false;
                                //System.out.println(x.key[pos]);
                                if (pos != x.n) {
                                        devider = x.key[pos];
                                        lt = x.child[pos]; 
                                        rt = x.child[pos+1];
                                } else {
                                        devider = x.key[pos-1];
                                        rt = x.child[pos];
                                        lt = x.child[pos-1];
                                        last = true;
                                        pos--;
                                }
                                
                                for (int i = pos; i < x.n-1; i++) {
                                        x.key[i] = x.key[i+1];
                                }
                                
//					for(int i = x.n + 1 ; i > pos ; i--) {
//						x.child[i-1] = x.child[i];
//					}
                                for(int i = pos+1; i < x.n; i++) {
                                        x.child[i] = x.child[i+1];
                                }
                                x.n--;
                                lt.key[lt.n++] = devider;
                                int numChild = 0;
                                //lt.child[lt.n] = rt.child[numChild++];
                                //Show(root);
                                for (int i = 0, j = lt.n; i < rt.n+1; i++,j++) {
                                        if (i < rt.n) {
                                                lt.key[j] = rt.key[i];
                                        }
                                        lt.child[j] = rt.child[i];
                                }
                                lt.n += rt.n;
                                if (x.n == 0) {
                                        if (x == root) {
                                                root = x.child[0];
                                        }
                                        x = x.child[0];
                                }
                                //Show(lt);
                                //Show(lt);
                                Remove(lt,key);

                                return;
                        }
                }
        }
}
public void Remove (int key) {
        bNode x = Search(root, key);
        if (x == null) {
            return;
        }
        Remove(root,key);
}



public void Task (int a, int b){
        Stack<Integer> st = new Stack<>();
        FindKeys(a,b,root,st);
        while (st.isEmpty() == false) {
//			System.out.println("--------------------");
//			System.out.println(st.peek());
//			Show(root);
//			System.out.println("--------------------");
                this.Remove(root,st.pop());
        }
}

private void FindKeys (int a, int b, bNode x, Stack<Integer> st){
        int i = 0;
        for (i = 0; i < x.n && x.key[i] < b; i++) {
                if ( x.key[i] > a  ) {
                        st.push(x.key[i]);
                }
        }
        if (!x.leaf) {
                for (int j = 0; j < i+1; j++) {
                        FindKeys(a,b,x.child[j],st);
                }
                //FindKeys(a,b,x.child[i],st);
        }
}

public boolean Contain(int k) {
        if (this.Search(root, k) != null) {
                return true;
        } else {
                return false;
        }
    }
void showr(){
        Show(root);
}
    public static void main(String[] args) {
        char[] arr={'f','s','q','k','c','l','h','t','v','w','m','r','n','p','a','b','x','y','d','z','e'};
        int degree;
        Scanner sc = new Scanner(System.in);
        degree=sc.nextInt();
        BTree  bt = new BTree(degree);
        char k = 'k';
        for(int i = 0;i<arr.length;i++){
             bt.Insert((int)(arr[i]));
          
             System.out.println("");
        }
           bt.showr();
    }
    
}
