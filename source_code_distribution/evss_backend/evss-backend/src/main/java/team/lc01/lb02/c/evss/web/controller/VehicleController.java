package team.lc01.lb02.c.evss.web.controller;

import java.util.*;
import team.lc01.lb02.c.evss.util.URL;
import team.lc01.lb02.c.evss.util.CODE;
import team.lc01.lb02.c.evss.domain.User;
import team.lc01.lb02.c.evss.util.Status;
import team.lc01.lb02.c.evss.domain.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.lc01.lb02.c.evss.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping(value = URL.VEHICLE)
@ResponseBody
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Add a vehicle to the database
     * when it has a unique serialnumber
     *
     * @param sn String - The unique serialnumber of the vehicle
     * @param type Integer - The vehicle type
     * @return Map - The added vehicle data
     */
    @RequestMapping(value = URL.VEHICLE_ADD, method = RequestMethod.POST)
    public Map<String, Object> addVehicleController(String sn, Integer type) {
        Map<String, Object> result = new HashMap<>();
        Vehicle vehicle;

        try {
            vehicle = new Vehicle();
            vehicle.setSn(sn);
            vehicle.setType(type);

            if (vehicleService.checkDuplicatedSN(sn)) {
                result.put("msg", "vehicle sn existed");
                result.put("code", CODE.WRONG_INPUT);
            } else {
                Vehicle savedVehicle = vehicleService.add(vehicle);
                result.put("code", CODE.SUCCESS);
                result.put("msg", "vehicle added success");
                result.put("data", savedVehicle);
            }
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            result.put("msg", "parameters invalid");
            result.put("code", CODE.WRONG_INPUT);
        }

        return result;
    }

    /**
     * Find vehicles by:
     * sn
     * type
     * sn and type
     * all vehicles
     *
     * @param sn String - A serialnumber String
     * @param type Integer - A valid vehicle type
     * @return Map - The results
     */
    @RequestMapping(value = URL.VEHICLE_FIND, method = RequestMethod.POST)
    public Map<String, Object> findVehicleController(String sn, Integer type) {
        Map<String, Object> result = new HashMap<>();
        Map<Integer, Vehicle> vehicles;
        Vehicle vehicle;

        if(sn == null) {
            if(type == null) {
                vehicles = vehicleService.findAllVehicles();
                result.put("code", CODE.SUCCESS);
                result.put("msg", String.format("%s vehicles in total", vehicles.size()));
                result.put("data", vehicles);
            } else {
                if(type == 1 || type == 2) {
                    vehicles = vehicleService.findVehiclesByType(type);
                    result.put("code", CODE.SUCCESS);
                    result.put("msg", String.format("%s vehicles in total", vehicles.size()));
                    result.put("data", vehicles);
                } else {
                    result.put("code", CODE.WRONG_INPUT);
                    result.put("msg", "no such vehicle type");
                }
            }
        } else {
            if(type == null) {
                vehicle = vehicleService.findVehicleBySN(sn);
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    result.put("msg", "no such vehicle");
                } else {
                    result.put("code", CODE.SUCCESS);
                    result.put("msg", "find success");
                    result.put("data", vehicle);
                }

            } else {
                vehicles = vehicleService.findVehiclesByType(type);
                vehicle = vehicleService.findVehicleBySN(sn);
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    result.put("msg", "no such vehicle");
                } else {
                    if(vehicles.containsValue(vehicle)) {
                        result.put("code", CODE.SUCCESS);
                        result.put("msg", "find success");
                        result.put("data", vehicle);
                    } else {
                        result.put("code", CODE.WRONG_INPUT);
                        result.put("msg", "no such vehicle");
                    }
                }
            }
        }

        return result;
    }

    /**
     * Find a vehicle by a serialnumber
     * then change its status when it has been found
     *
     * @param sn String - The serialnumber candidate
     * @param status Integer - A valid status number
     * @return Map - The results
     */
    @RequestMapping(value = URL.VEHICLE_STATUS, method = RequestMethod.POST)
    public Map<String, Object> statusController(String sn, Integer status) {
        Map<String, Object> result = new HashMap<>();
        Vehicle vehicle;
        List<Integer> statuses = Arrays.asList(-1, 0, 1, 2, 3, 4);

        if(sn == null || status == null) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "parameters invalid");
        } else {
            if(statuses.contains(status)) {
                vehicle = vehicleService.changeStatusBySN(sn, status);
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    result.put("msg", "no such vehicle");
                } else {
                    result.put("code", CODE.SUCCESS);
                    result.put("msg", "change status success");
                    result.put("data", vehicle);
                }
            } else {
                result.put("code", CODE.WRONG_INPUT);
                result.put("msg", "no such status");
            }
        }

        return result;
    }

    /**
     * Assign a maintainer to a vehicle - with full parameters
     * Get the maintenance records of a vehicle - with sn only
     *
     * @param sn String - The serialnumber candidate
     * @param email String - The email of a maintainer
     * @return Map - The results
     */
    @RequestMapping(value = URL.VEHICLE_MAINTAIN, method = RequestMethod.POST)
    public Map<String, Object> maintainController(String sn, String email) {
        Map<String, Object> result = new HashMap<>();
        Vehicle vehicle;


        if(sn == null) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "parameters invalid");
        } else {
            if(email == null) {
                vehicle = vehicleService.findVehicleBySN(sn);
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    result.put("msg", "no such vehicle");
                } else {
                    result.put("code", CODE.SUCCESS);
                    result.put("msg", "get records success");
                    result.put("data", vehicle.getMaintainRecords());
                }
            } else {
                vehicle = vehicleService.addMaintainerBySN(sn, email);
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    result.put("msg", "make sure the vehicle is registered and the email belongs to an operator or an admin");
                } else {
                    vehicle = vehicleService.changeStatusBySN(sn, Status.UNDER_MAINTENANCE);
                    result.put("code", CODE.SUCCESS);
                    result.put("msg", "set maintainer success");
                    result.put("data", vehicle);
                }
            }
        }

        return result;
    }

    /**
     * Get the user of a vehicle
     * Assign a user to a vehicle
     * Remove a user from a vehicle
     *
     * @param sn String - The serialnumber candidate
     * @param email String - The email of a maintainer
     * @param remove Integer - The remove switch
     * @return Map - The results
     */
    @RequestMapping(value = URL.VEHICLE_USER, method = RequestMethod.POST)
    public Map<String, Object> userController(String sn, String email, Integer remove) {
        Map<String, Object> result = new HashMap<>();
        Vehicle vehicle;
        if(remove == null) {
            remove = 0;
        }

        if(sn == null) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "parameters invalid");
        } else {
            if(email == null) {
                if(remove == 1) {
                    vehicle = vehicleService.addUserBySN(sn, email, remove);
                } else {
                    vehicle = vehicleService.findVehicleBySN(sn);
                }
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    if(remove == 1) {
                        result.put("msg", "not assigned a user");
                    } else {
                        result.put("msg", "no such vehicle");
                    }
                } else {


                    if(remove == 1) {
                        result.put("code", CODE.SUCCESS);
                        result.put("msg", "remove user success");
                        result.put("data", vehicle);
                    } else {
                        User user = vehicle.getUser();
                        if(user == null) {
                            result.put("code", CODE.WRONG_INPUT);
                            result.put("msg", "not assigned a user");
                        } else {
                            result.put("code", CODE.SUCCESS);
                            result.put("msg", "get user success");
                            result.put("data", vehicle.getUser());
                        }
                    }

                }
            } else {
                vehicle = vehicleService.addUserBySN(sn, email, remove);
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    if(remove == 1) {
                        result.put("msg", "not assigned a user");
                    } else {
                        result.put("msg", "unknown sn or email");
                    }
                } else {
                    result.put("code", CODE.SUCCESS);
                    if(remove == 1) {
                        result.put("msg", "remove user success");
                    } else {
                        result.put("msg", "set user success");
                    }
                    result.put("data", vehicle);
                }
            }
        }

        return result;
    }

    /**
     * Get vehicle location by a sn
     * Set vehicle location by its sn and a latitude,longitude location
     *
     * @param sn String - The serialnumber candidate
     * @param latitude String - A latitude location
     * @param longitude String - A longitude location
     * @return Map - The results
     */
    @RequestMapping(value = URL.VEHICLE_LOC, method = RequestMethod.POST)
    public Map<String, Object> locationController(String sn, String latitude, String longitude) {
        Map<String, Object> result = new HashMap<>();
        String location = String.format("%s,%s", latitude, longitude);
        Vehicle vehicle;

        if(sn == null) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "parameters invalid");
        } else {
            if(location == null) {
                vehicle = vehicleService.findVehicleBySN(sn);
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    result.put("msg", "no such vehicle");
                } else {
                    result.put("code", CODE.SUCCESS);
                    result.put("msg", "get location success");
                    result.put("data", vehicle.getLocation());
                }
            } else {
                vehicle = vehicleService.setLocationBySN(sn, location);
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    result.put("msg", "no such vehicle");
                } else {
                    result.put("code", CODE.SUCCESS);
                    result.put("msg", "set location success");
                    result.put("data", vehicle);
                }
            }
        }

        return result;
    }

    /**
     * Charge a vehicle with charging percentage
     * Get a vehicle battery life with sn only
     *
     * @param sn String - The serialnumber candidate
     * @param charge Double - The charging percentage
     * @return Map - The results
     */
    @RequestMapping(value = URL.VEHICLE_CHARGE, method = RequestMethod.POST)
    public Map<String, Object> chargeController(String sn, Double charge) {
        Map<String, Object> result = new HashMap<>();
        Vehicle vehicle;

        if(sn == null) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "parameters invalid");
        } else {
            if(charge == null) {
                vehicle =  vehicleService.findVehicleBySN(sn);
                if(vehicle == null) {
                    result.put("code", CODE.WRONG_INPUT);
                    result.put("msg", "no such vehicle");
                } else {
                    result.put("code", CODE.SUCCESS);
                    result.put("msg", "get battery life success");
                    result.put("data", vehicle.getBattery());
                }
            } else {
                vehicle = vehicleService.chargeBySN(sn, charge);
                result.put("code", CODE.SUCCESS);
                result.put("msg", "charge success");
                result.put("data", vehicle);
            }
        }

        return result;
    }
}
