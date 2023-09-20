package team.lc01.lb02.c.evss.dao;

import java.util.List;
import team.lc01.lb02.c.evss.domain.Vehicle;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao extends BaseDao<Vehicle> {

    public VehicleDao() {
        super(Vehicle.class);
    }

    /**
     * Find all vehicles
     *
     * @return List - All vehicles or null if no vehicle
     */
    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = em.createNamedQuery("Vehicle.viewAll", Vehicle.class).getResultList();

        return vehicles.size() > 0 ? vehicles : null;
    }

    /**
     * Find a vehicle by a type code
     *
     * @param type Integer - The type code of vehicle
     * @return List - The found vehicles or null if no such type
     */
    public List<Vehicle> findVehiclesByType(Integer type) {
        List<Vehicle> vehicles = em.createNamedQuery("Vehicle.findByType", Vehicle.class)
                .setParameter("type", type).getResultList();

        return vehicles.size() > 0 ? vehicles : null;
    }

    /**
     * Find a vehicle by a serialnumber
     *
     * @param sn String - The serialnumber candidate
     * @return Vehicle - The found vehicle or null if no such vehicle
     */
    public Vehicle findVehicleBySN(String sn) {
        List<Vehicle> vehicles = em.createNamedQuery("Vehicle.findBySN", Vehicle.class)
                .setParameter("sn", sn).getResultList();

        return vehicles.size() == 1 ? vehicles.get(0) : null;
    }

    /**
     * Find and change a vehicle status
     *
     * @param sn String - The serialnumber candidate
     * @param status Integer - A valid status number
     * @return Vehicle - Updated vehicle or 'null' if no such vehicle
     */
    public Vehicle changeStatusBySN(String sn, Integer status) {
        Vehicle vehicle = findVehicleBySN(sn);
        if(vehicle == null) {
            return null;
        } else {
            vehicle.setStatus(status);
            return vehicle;
        }
    }

}
