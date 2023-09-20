package team.lc01.lb02.c.evss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.lc01.lb02.c.evss.dao.UserDao;
import team.lc01.lb02.c.evss.dao.VehicleDao;
import team.lc01.lb02.c.evss.domain.Ticket;
import team.lc01.lb02.c.evss.domain.User;
import team.lc01.lb02.c.evss.domain.Vehicle;
import team.lc01.lb02.c.evss.util.Role;
import team.lc01.lb02.c.evss.util.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class VehicleService {

    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private UserDao userDao;

    /**
     * Add a vehicle
     *
     * @param vehicle Vehicle - An initialised Vehicle instance
     * @return Vehicle
     */
    public Vehicle add(Vehicle vehicle) {
        return vehicleDao.save(vehicle);
    }

    /**
     * Find all vehicles
     *
     * @return Map - vehicles or 'null' if no vehicle
     */
    public Map<Integer, Vehicle> findAllVehicles() {
        if (vehicleDao.findAllVehicles().isEmpty()) {
            return null;
        } else {
            List<Vehicle> vehicleList = vehicleDao.findAllVehicles();
            Map<Integer, Vehicle> vehicles = new HashMap<>();
            for (int i = 0; i < vehicleList.size(); i++) {
                vehicles.put(i, vehicleList.get(i));
            }
            return vehicles;
        }

    }

    /**
     * Find vehicles by a type
     *
     * @param type Integer - A valid vehicle type
     * @return Map - vehicles or 'null' if no such type
     */
    public Map<Integer, Vehicle> findVehiclesByType(Integer type) {
        if (vehicleDao.findVehiclesByType(type).isEmpty()) {
            return null;
        } else {
            List<Vehicle> vehicleList = vehicleDao.findVehiclesByType(type);
            Map<Integer, Vehicle> vehicles = new HashMap<>();
            for (int i = 0; i < vehicleList.size(); i++) {
                vehicles.put(i, vehicleList.get(i));
            }
            return vehicles;
        }

    }

    /**
     * Find a vehicle by a serialnumber
     *
     * @param sn String - A serialnumber String
     * @return Vehicle - the found vehicle or 'null' if unknown
     */
    public Vehicle findVehicleBySN(String sn) {
        if (vehicleDao.findVehicleBySN(sn) == null) {
            return null;
        } else {
            return vehicleDao.findVehicleBySN(sn);
        }

    }

    /**
     * Find and change a vehicle status
     *
     * @param sn     String - The serialnumber candidate
     * @param status Integer - A valid status number
     * @return Vehicle - Updated vehicle or 'null' if no such vehicle
     */
    public Vehicle changeStatusBySN(String sn, Integer status) {
        Vehicle vehicle = vehicleDao.changeStatusBySN(sn, status);

        if (vehicle == null) {
            return null;
        } else {
            return vehicleDao.save(vehicle);
        }
    }

    /**
     * Assign a maintainer to a vehicle
     * and updates the maintenance records
     *
     * @param sn    String - The serialnumber candidate
     * @param email String - The email of an operator
     * @return Vehicle - Updated vehicle or 'null' if failed
     */
    public Vehicle addMaintainerBySN(String sn, String email) {
        Vehicle vehicle = findVehicleBySN(sn);

        if (userDao.findUserByEmail(email).isPresent()) {
            if (vehicle == null) {
                return null;
            } else {
                User maintainer = userDao.findUserByEmail(email).get();
                if (maintainer.getRole() == Role.OPERATOR || maintainer.getRole() == Role.ADMIN) {
                    LocalDateTime nowTime = LocalDateTime.now();
                    User lastMaintainer = vehicle.getMaintainer();
                    Map<String, Object> result;
                    if (lastMaintainer == null) {
                        result = new LinkedHashMap<>();
                    } else {
                        String lastResult = vehicle.getMaintainRecords();
                        if (lastResult != null) {
                            result = JsonUtil.toBean(lastResult, LinkedHashMap.class);
                        } else {
                            result = new LinkedHashMap<>();
                        }
                    }
                    result.put(nowTime.toString(), email);
                    vehicle.setMaintainer(maintainer);
                    vehicle.setMaintainRecords(JsonUtil.toJson(result));

                    return vehicleDao.save(vehicle);
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Assign a user to a vehicle
     *
     * @param sn    String - The serialnumber candidate
     * @param email String - The email of an operator
     * @return Vehicle - Updated vehicle or 'null' if failed
     */
    public Vehicle addUserBySN(String sn, String email, Integer remove) {
        Vehicle vehicle = findVehicleBySN(sn);

        if (remove == 1) {
            if (vehicle == null) {
                return null;
            } else {
                if (vehicle.getUser() == null) {
                    return null;
                } else {
                    vehicle.setUser(null);
                    return vehicleDao.save(vehicle);
                }
            }
        } else {
            if (userDao.findUserByEmail(email).isPresent()) {
                if (vehicle == null) {
                    return null;
                } else {
                    User user = userDao.findUserByEmail(email).get();
                    vehicle.setUser(user);
                    return vehicleDao.save(vehicle);
                }
            } else {
                return null;
            }
        }
    }

    /**
     * Set location by valid serialnumber
     *
     * @param sn       String - The serialnumber candidate
     * @param location String - A 'latitude,longitude' format location
     * @return Vehicle - Updated vehicle or 'null' if no such vehicle
     */
    public Vehicle setLocationBySN(String sn, String location) {
        Vehicle vehicle = findVehicleBySN(sn);

        if (vehicle == null) {
            return null;
        } else {
            vehicle.setLocation(location);
            return vehicleDao.save(vehicle);
        }
    }

    /**
     * Get location by valid serialnumber
     *
     * @param sn String - The serialnumber candidate
     * @return Double[] - [latitude, longitude]
     */
    public Double[] getLocationBySN(String sn) {
        Vehicle vehicle = findVehicleBySN(sn);
        Double[] locations;

        if (vehicle == null) {
            return null;
        } else {
            locations = Arrays.stream(vehicle.getLocation().split(","))
                    .map(Double::valueOf)
                    .toArray(Double[]::new);
            return locations;
        }
    }

    public Vehicle chargeBySN(String sn, Double charge) {
        Map<String, Object> result;
        Map<String, Object> c_result = new LinkedHashMap<>();
        LocalDateTime nowTime = LocalDateTime.now();
        Vehicle vehicle = findVehicleBySN(sn);

        if (vehicle == null) {
            return null;
        } else {
            double currentBattery = vehicle.getBattery();
            if (currentBattery == 100 && charge == null) {
                return vehicle;
            } else {
                double totalBattery = currentBattery + charge;
                double realBattery;
                String oldChargingRecords = vehicle.getChargingRecords();
                result = JsonUtil.toBean(oldChargingRecords, LinkedHashMap.class);
                if (result == null) {
                    result = new LinkedHashMap<>();
                }
                c_result.put("start", currentBattery);
                if (charge >= 0) {
                    vehicle.setChargingCycle(vehicle.getChargingCycle() + (totalBattery >= 100 ? (100 - currentBattery) / 100 : charge / 100));
                    realBattery = (totalBattery >= 100 ? 100 : totalBattery);
                    vehicle.setBattery(realBattery);
                } else {
                    realBattery = (totalBattery <= 0 ? 0 : totalBattery);
                    vehicle.setBattery(realBattery);
                }
                c_result.put("end", realBattery);
                result.put(nowTime.toString(), c_result);
                vehicle.setChargingRecords(JsonUtil.toJson(result));

                return vehicleDao.save(vehicle);
            }
        }
    }

    /**
     * Find all tickets attached to a vehicle
     *
     * @param sn String - The serialnumber of a vehicle
     * @return Set of tickets
     */
    public Map<Integer, Ticket> findAllTickets(String sn) {
        Vehicle vehicle = findVehicleBySN(sn);
        Set<Ticket> tickets;
        Map<Integer, Ticket> m_tickets = new HashMap<>();
        if (vehicle == null) {
            return null;
        } else {
            tickets = vehicle.getTickets();
            if (tickets == null || tickets.isEmpty()) {
                return null;
            } else {
                int i = 0;
                for (Ticket tk : tickets) {
                    m_tickets.put(i, tk);
                    i += 1;
                }
                return m_tickets;
            }

        }
    }

    /**
     * Check the duplication of serialnumber
     *
     * @param sn String - The serialnumber candidate
     * @return Boolean - if the check passed
     */
    public boolean checkDuplicatedSN(String sn) {
        return vehicleDao.findVehicleBySN(sn) != null;
    }

}
