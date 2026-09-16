package week4.class_problems;

public class BusRoute {
    private String routeCode;
    private String routeName;
    private int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 5);
    }

    public int compareTo(BusRoute other) {
        if (this.priority != other.priority) {
            return Integer.compare(this.priority, other.priority);
        }

        int codeCompare = this.routeCode.compareToIgnoreCase(other.routeCode);

        if (codeCompare != 0) {
            return codeCompare;
        }

        return this.routeName.compareToIgnoreCase(other.routeName);
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        BusRoute[] result = routes.clone();

        for (int i = 1; i < result.length; i++) {
            BusRoute current = result[i];
            int j = i - 1;

            while (j >= 0 && result[j].compareTo(current) > 0) {
                result[j + 1] = result[j];
                j--;
            }

            result[j + 1] = current;
        }

        return result;
    }

    public static void main(String[] args) {
        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] ranked = rankRoutes(routes);

        for (BusRoute route : ranked) {
            System.out.println(route.routeCode);
        }
    }
}