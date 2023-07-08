package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

type basicInfo struct{
	identity string `json:"identity"`
	name     string `json :"name"`
	mobile   string `json:"mobile"`
}


func (t *basicInfo) Init (stub shim.ChaincodeStubInterface) pb.Response{
	return shim.Success(nil)
}

func (t *basicInfo) Invoke(stub shim.ChaincodeStubInterface) pb.Response{
	funcName,args := stub.GetFunctionAndParameters()
	if(funcName=="save"){
		return t.saveBasic(stub,args)
	}else if(funcName=="query"){
		return t.queryBasic(stub,args)
	}else{
		return shim.Error("no such function")
	}
}

func (t *basicInfo) saveBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if(len(args)!=2){
		return shim.Error("except two args")
	}else{
		err:=stub.PutState(args[0],[]byte(args[1]))
		if(err!=nil) {
			return shim.Error(err.Error())
		}
		return shim.Success(nil)
	}

}

func (t *basicInfo) queryBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response{

	if(len(args)!=1){
		return shim.Error("except one arg")
	}else{
	  value,err :=stub.GetState(args[0])
	  if(err!=nil){
	  	shim.Error("no data found")
	  }
	  return shim.Success(value)
	}
}

func main(){
	err:=shim.Start(new(basicInfo))
	if(err!=nil){
		fmt.Println("emr basicInfo chaincode start error")
	}
}