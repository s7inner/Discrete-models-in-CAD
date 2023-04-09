package utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;

public class MaxFlowAndPathData {

    public int maxFlowVal;
    public LinkedList pathList = new LinkedList<>();

    public int[][] currentMinFlow;

    public MaxFlowAndPathData(int maxFlowVal, LinkedList pathList, int countOfVertex) {
        this.maxFlowVal = maxFlowVal;
        this.pathList = pathList;

        this.currentMinFlow = new int[countOfVertex][countOfVertex];
        Arrays.stream(currentMinFlow).forEach(a -> Arrays.fill(a, 0));

        for (int i = pathList.size()-1; i >=1 ; i--) {
            currentMinFlow[(Integer)pathList.get(i-1)][(Integer)pathList.get(i)] += maxFlowVal;
        }
    }

    public MaxFlowAndPathData(int maxFlowVal, LinkedList pathList, int[][] previousMinFlow) {
        this.maxFlowVal = maxFlowVal;
        this.pathList = pathList;

        this.currentMinFlow = Arrays.stream(previousMinFlow).map(int[]::clone).toArray(int[][]::new);
//        Arrays.stream(currentMinFlow).forEach(a -> Arrays.fill(a, 0));

        for (int i = pathList.size()-1; i >=1 ; i--) {
            currentMinFlow[(Integer)pathList.get(i-1)][(Integer)pathList.get(i)] += maxFlowVal;
        }
    }





    public MaxFlowAndPathData() {
    }

    public int getMaxFlowVal() {
        return maxFlowVal;
    }

    public void setMaxFlowVal(int maxFlowVal) {
        this.maxFlowVal = maxFlowVal;
    }

    public LinkedList getPathList() {
        return pathList;
    }

    public void setPathList(LinkedList pathList) {
        this.pathList = pathList;
    }
}
