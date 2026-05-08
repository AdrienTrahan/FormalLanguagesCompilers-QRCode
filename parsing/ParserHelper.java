package parsing;

import java.util.HashMap;

public class ParserHelper {
    public HashMap<String, String> variableNameMap;
    public HashMap<String, String> functionNameMap;

    public ParserHelper(HashMap<String, String> variableNameMap, HashMap<String, String> functionNameMap) {
        this.variableNameMap = variableNameMap;
        this.functionNameMap = functionNameMap;
    }

    public Integer getOptimizedHandle(String handle) {
        if (variableNameMap.get(handle) != null) return Integer.parseInt(variableNameMap.get(handle));
        return Integer.parseInt(handle);
    }

    public Integer getOptimizedFunctionName(String functionName) {
        if (functionNameMap.get(functionName) != null) return Integer.parseInt(functionNameMap.get(functionName));
        return Integer.parseInt(functionName);
    }
}