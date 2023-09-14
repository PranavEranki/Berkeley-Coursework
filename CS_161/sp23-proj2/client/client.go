package client

// CS 161 Project 2

// You MUST NOT change these default imports. ANY additional imports
// may break the autograder!

import (
	"encoding/json"
	"strconv"

	userlib "github.com/cs161-staff/project2-userlib"
	"github.com/google/uuid"

	// hex.EncodeToString(...) is useful for converting []byte to string

	// Useful for string manipulation

	// Useful for formatting strings (e.g. `fmt.Sprintf`).
	"fmt"

	// Useful for creating new error messages to return using errors.New("...")
	"errors"

	// Optional.
	_ "strconv"
)

// This serves two purposes: it shows you a few useful primitives,
// and suppresses warnings for imports not being used. It can be
// safely deleted!
func someUsefulThings() {

	// Creates a random UUID.
	randomUUID := uuid.New()

	// Prints the UUID as a string. %v prints the value in a default format.
	// See https://pkg.go.dev/fmt#hdr-Printing for all Golang format string flags.
	userlib.DebugMsg("Random UUID: %v", randomUUID.String())

	// Creates a UUID deterministically, from a sequence of bytes.
	hash := userlib.Hash([]byte("user-structs/alice"))
	deterministicUUID, err := uuid.FromBytes(hash[:16])
	if err != nil {
		// Normally, we would `return err` here. But, since this function doesn't return anything,
		// we can just panic to terminate execution. ALWAYS, ALWAYS, ALWAYS check for errors! Your
		// code should have hundreds of "if err != nil { return err }" statements by the end of this
		// project. You probably want to avoid using panic statements in your own code.
		panic(errors.New("An error occurred while generating a UUID: " + err.Error()))
	}
	userlib.DebugMsg("Deterministic UUID: %v", deterministicUUID.String())

	// Declares a Course struct type, creates an instance of it, and marshals it into JSON.
	type Course struct {
		name      string
		professor []byte
	}

	course := Course{"CS 161", []byte("Nicholas Weaver")}
	courseBytes, err := json.Marshal(course)
	if err != nil {
		panic(err)
	}

	userlib.DebugMsg("Struct: %v", course)
	userlib.DebugMsg("JSON Data: %v", courseBytes)

	// Generate a random private/public keypair.
	// The "_" indicates that we don't check for the error case here.
	var pk userlib.PKEEncKey
	var sk userlib.PKEDecKey
	pk, sk, _ = userlib.PKEKeyGen()
	userlib.DebugMsg("PKE Key Pair: (%v, %v)", pk, sk)

	// Here's an example of how to use HBKDF to generate a new key from an input key.
	// Tip: generate a new key everywhere you possibly can! It's easier to generate new keys on the fly
	// instead of trying to think about all of the ways a key reuse attack could be performed. It's also easier to
	// store one key and derive multiple keys from that one key, rather than
	originalKey := userlib.RandomBytes(16)
	derivedKey, err := userlib.HashKDF(originalKey, []byte("mac-key"))
	if err != nil {
		panic(err)
	}
	userlib.DebugMsg("Original Key: %v", originalKey)
	userlib.DebugMsg("Derived Key: %v", derivedKey)

	// A couple of tips on converting between string and []byte:
	// To convert from string to []byte, use []byte("some-string-here")
	// To convert from []byte to string for debugging, use fmt.Sprintf("hello world: %s", some_byte_arr).
	// To convert from []byte to string for use in a hashmap, use hex.EncodeToString(some_byte_arr).
	// When frequently converting between []byte and string, just marshal and unmarshal the data.
	//
	// Read more: https://go.dev/blog/strings

	// Here's an example of string interpolation!
	_ = fmt.Sprintf("%s_%d", "file", 1)
}

// This is the type definition for the User struct.
// A Go struct is like a Python or Java class - it can have attributes
// (e.g. like the Username attribute) and methods (e.g. like the StoreFile method below).
type User struct {
	Username   string
	PrivateKey userlib.PrivateKeyType
	SignKey    userlib.PrivateKeyType
	// You can add other attributes here if you want! But note that in order for attributes to
	// be included when this struct is serialized to/from JSON, they must be capitalized.
	// On the flipside, if you have an attribute that you want to be able to access from
	// this struct's methods, but you DON'T want that value to be included in the serialized value
	// of this struct that's stored in datastore, then you can use a "private" variable (e.g. one that
	// begins with a lowercase letter).
}

type File struct {
	// this struct will hold the encryption key, HMAC key for encrypting and verifying
	// Fragments of data
	// Here we will also store the UUID of Fragments and a list of direct children that have access
	FragmentsUUID uuid.UUID
	FragSKey      []byte
	FragHMACK     []byte
	Children      []Invitation
}

type Fragments struct {
	OwnerUN string
	FName   string
	Cnt     int // the counter will be used to keep track of the number of fragments this
	// file is broken into
	EKey  []byte // encryption key for the data
	HMACK []byte // HMAC key for appends and stores after encrypting
}

// this is the SharedFile struct
type ShFile struct {
	UName     string // this is the username of the person the file was shared with
	IsOwner   bool   // indicates whether UName is the original owner
	FragSKey  []byte // symmetric key for fragments
	FragHMACK []byte // HMAC for Fragments
	FragUUID  uuid.UUID
	FileUUID  uuid.UUID // relevant for file owner's ShFile only
	FKey      []byte    // relevant for file owner's ShFile only
	FHMACK    []byte    // relevant for file owner's ShFile only
}

type Invitation struct {
	SFUUID  uuid.UUID // ID for the ShFile struct
	SFK     []byte    // ShFile Key
	SFHMACK []byte    // SHFile HMACK
}

// Helpers

// This function generates the UUID for the User Struct
// to access it in the datastore
func getUserID(username string) uuid.UUID {
	hash := userlib.Hash([]byte(username + "_usr"))
	id, _ := uuid.FromBytes(hash[:16])
	return id
}

func deriveKeysFromR(rKey []byte, length uint) ([]byte, []byte, error) {
	hmack, err := userlib.HashKDF(rKey, []byte("HMAC"))
	if err != nil {
		return nil, nil, err
	}
	symKey, err := userlib.HashKDF(rKey, []byte("Enc"))
	if err != nil {
		return nil, nil, err
	}
	return symKey[:length], hmack[:length], nil

}

// a method to encrypt then HMAC and return the encrypted and HMACed data
func EncryptHMAC(encKey []byte, hMACK []byte, data []byte) (encData []byte, err error) {
	encData = userlib.SymEnc(encKey, userlib.RandomBytes(16), data)
	hmac, err := userlib.HMACEval(hMACK, encData)
	if err != nil {
		return nil, err
	}
	encData = append(encData, hmac...)
	return encData, nil

}

// this method takes in the encrypted then hmaced data verifies the hmac
// then decrypts if hmac valid and returns the decrypted data
func VerifyHMACDecrypt(encKey []byte, hMACK []byte, data []byte) (decData []byte, err error) {
	encData := data[:len(data)-64]
	hmac := data[len(data)-64:]
	encDataHMAC, err := userlib.HMACEval(hMACK, encData)
	if err != nil {
		return nil, err
	}
	if userlib.HMACEqual(encDataHMAC, hmac) {
		return userlib.SymDec(encKey, encData), nil
	}
	return nil, errors.New("user data in the datastore cannot be verified or authenticated")
}

func getInviteUID(username string, fName string) uuid.UUID {
	uidBytes := userlib.Hash([]byte(username + fName + "F"))[:16]
	uid, _ := uuid.FromBytes(uidBytes)
	return uid
}

func getPubUserKeys(username string) (userlib.PublicKeyType, userlib.DSVerifyKey, error) {
	// get encryption key
	encKey, ok := userlib.KeystoreGet(username + "EncryptK")
	if !ok {
		return userlib.PublicKeyType{}, userlib.DSVerifyKey{}, errors.New("public Key does not exist")
	}
	// get verify key
	verifyKey, ok := userlib.KeystoreGet(username + "VerifyK")
	if !ok {
		return userlib.PublicKeyType{}, userlib.DSVerifyKey{}, errors.New("public Verification Key does not exist")
	}
	return encKey, verifyKey, nil
}

func verifyDecrypt(encBytes []byte, decKey userlib.PrivateKeyType,
	verifyK userlib.DSVerifyKey) ([]byte, error) {
	// check if the data has been tampered with
	if len(encBytes) < 256 {
		return nil, errors.New("encryption invitation has been tampered with: len < 256")
	}
	err := userlib.DSVerify(verifyK, encBytes[:len(encBytes)-256],
		encBytes[len(encBytes)-256:])
	if err != nil {
		return nil, err
	}
	decInvite, err := userlib.PKEDec(decKey, encBytes[:len(encBytes)-256])
	if err != nil {
		return nil, err
	}
	return decInvite, nil
}

// this method fetches the invitation for a given user and a filename
func fetchInvite(user *User, fName string) (invite *Invitation, ok bool, err error) {
	key := getInviteUID(user.Username, fName)
	encInviteBytes, ok := userlib.DatastoreGet(key)
	if !ok {
		return nil, false, nil
	}
	// get the sig verification key
	_, verifyKey, err := getPubUserKeys(user.Username)
	if err != nil {
		return nil, false, err
	}
	// decrypt and verify signature
	inviteBytes, err := verifyDecrypt(encInviteBytes, user.PrivateKey, verifyKey)
	if err != nil {
		return nil, false, err
	}
	// unmarshall
	var tmpInv Invitation
	err = json.Unmarshal(inviteBytes, &tmpInv)
	if err != nil {
		return nil, false, err
	}
	// return the pointer
	return &tmpInv, true, nil
}

func getShFile(uid uuid.UUID, encK []byte,
	hMACK []byte) (*ShFile, error) {
	// fetch ShFileBytes by uid from datastore
	encShFile, ok := userlib.DatastoreGet(uid)
	if !ok {
		return nil, errors.New("shared file with this uid does not exist")
	}
	decShFile, err := VerifyHMACDecrypt(encK, hMACK, encShFile)
	if err != nil {
		return nil, err
	}
	// unmarshall
	var shFile ShFile
	err = json.Unmarshal(decShFile, &shFile)
	if err != nil {
		return nil, err
	}
	// return
	return &shFile, nil
}

// function that gets the Fragments w uid from DataStore verifies
// decrypts and returns a pointer to it
func getFragments(uid uuid.UUID, symKey []byte, hMACK []byte) (*Fragments, error) {
	encFragBytes, ok := userlib.DatastoreGet(uid)
	if !ok {
		return nil, errors.New("no such Fragments in DS")
	}
	decFragBytes, err := VerifyHMACDecrypt(symKey, hMACK, encFragBytes)
	if err != nil {
		return nil, err
	}
	// unmarshal
	var fragments Fragments
	err = json.Unmarshal(decFragBytes, &fragments)
	if err != nil {
		return nil, err
	}
	return &fragments, nil
}

// a function that generates a random uuid and 2 16 byte keys
func structInitIDKeys() (uuid.UUID, []byte, []byte) {
	return uuid.New(), userlib.RandomBytes(16), userlib.RandomBytes(16)
}

// this function makes three structs: File Fragments and ShFile for initialization
// then it returns three pointers to these structs
func makeBasicStructs(user *User, fName string) (*File, *Fragments, *ShFile) {

	var shFile ShFile
	var file File
	var fragments Fragments

	fragmentsUID, fragmentsEnc, fragmentsHMAC := structInitIDKeys()
	fragments.OwnerUN = user.Username
	fragments.FName = fName
	fragments.Cnt = 0
	fragments.EKey = fragmentsEnc
	fragments.HMACK = fragmentsHMAC

	fileUID, fileEnc, fileHMAC := structInitIDKeys()
	file.FragHMACK = fragmentsHMAC
	file.FragSKey = fragmentsEnc
	file.FragmentsUUID = fragmentsUID
	file.Children = []Invitation{}

	shFile.FHMACK = fileHMAC
	shFile.FKey = fileEnc
	shFile.FileUUID = fileUID
	shFile.FragHMACK = fragmentsHMAC
	shFile.FragSKey = fragmentsEnc
	shFile.FragUUID = fragmentsUID
	shFile.IsOwner = true
	shFile.UName = user.Username

	return &file, &fragments, &shFile
}

func EncHMACStore(data []byte, encK []byte, hMACK []byte, uid uuid.UUID) error {
	encBytes, err := EncryptHMAC(encK, hMACK, data)
	if err != nil {
		return err
	}
	userlib.DatastoreSet(uid, encBytes)
	return nil
}

func initFile(user *User, fName string) error {

	// make the three basic structures for the file
	file, fragments, shFile := makeBasicStructs(user, fName)

	// store sh file
	// generate uuid and keys for sharedFile
	shFileUID, shFileEnc, shFileMACK := structInitIDKeys()
	shFileBytes, err := json.Marshal(*shFile)
	if err != nil {
		return err
	}
	err = EncHMACStore(shFileBytes, shFileEnc, shFileMACK, shFileUID)
	if err != nil {
		return err
	}
	// store file
	fileBytes, err := json.Marshal(*file)
	if err != nil {
		return err
	}
	err = EncHMACStore(fileBytes, shFile.FKey, shFile.FHMACK, shFile.FileUUID)
	if err != nil {
		return err
	}
	// store fragments
	fragmentsBytes, err := json.Marshal(fragments)
	if err != nil {
		return err
	}
	err = EncHMACStore(fragmentsBytes, shFile.FragSKey, shFile.FragHMACK, shFile.FragUUID)
	if err != nil {
		return err
	}

	inviteUID, err := genInviteUID(user.Username, fName)
	if err != nil {
		return err
	}
	createStoreInvite(shFileUID, shFileEnc, shFileMACK, user, user.Username, inviteUID)
	return nil
}

func genInviteUID(username string, fName string) (uuid.UUID, error) {
	uid, err := uuid.FromBytes(userlib.Hash([]byte(username + fName + "F"))[:16])
	if err != nil {
		return uuid.UUID{}, err
	}
	return uid, nil
}

func createStoreInvite(ShFID uuid.UUID, ShFK []byte, sFHMACK []byte, sender *User, recipient string, inviteID uuid.UUID) (*Invitation, error) {
	var invite Invitation
	invite.SFUUID = ShFID
	invite.SFK = ShFK
	invite.SFHMACK = sFHMACK
	pubK, _, err := getPubUserKeys(recipient)
	if err != nil {
		return nil, err
	}
	raw_data, err := json.Marshal(invite)
	if err != nil {
		return nil, err
	}
	// encrypt
	enc_data, err := userlib.PKEEnc(pubK, raw_data)
	if err != nil {
		return nil, err
	}
	// sign
	sign, err := userlib.DSSign(sender.SignKey, enc_data)
	if err != nil {
		return nil, err
	}
	signed_data := append(enc_data, sign...)
	//store
	userlib.DatastoreSet(inviteID, signed_data)
	return &invite, err
}

func genFragID(username string, fName string, cnt int) (uuid.UUID, error) {
	str := username + fName + strconv.Itoa(cnt) + "frag"
	fragID, err := uuid.FromBytes(userlib.Hash([]byte(str))[:16])
	if err != nil {
		return uuid.UUID{}, err
	}
	return fragID, nil
}

// this function takes in a Fragments structure, then decrypts,
// verifies and concatenates the Fragments in order
func glueFragments(fragments *Fragments, encKey []byte, hMACK []byte) ([]byte, error) {
	var content []byte
	for i := 0; i < fragments.Cnt; i++ {
		fragID, err := genFragID(fragments.OwnerUN, fragments.FName, i)
		if err != nil {
			return nil, err
		}
		frag, err := getFragment(fragID, encKey, hMACK)
		if err != nil {
			return nil, err
		}
		content = append(content, frag...)
	}
	return content, nil
}

func getFragment(id uuid.UUID, encKey []byte, hMACK []byte) ([]byte, error) {
	encFragBytes, ok := userlib.DatastoreGet(id)
	if !ok {
		return nil, errors.New("fragment not found")
	}
	fragBytes, err := VerifyHMACDecrypt(encKey, hMACK, encFragBytes)
	if err != nil {
		return nil, err
	}
	return fragBytes, nil
}

func getFile(id uuid.UUID, encKey []byte, hMACK []byte) (*File, error) {
	encFileBytes, ok := userlib.DatastoreGet(id)
	if !ok {
		return nil, errors.New("file not found")
	}
	fileBytes, err := VerifyHMACDecrypt(encKey, hMACK, encFileBytes)
	if err != nil {
		return nil, err
	}
	var retF File
	err = json.Unmarshal(fileBytes, &retF)
	if err != nil {
		return nil, err
	}
	return &retF, nil
}

// this is a function that copies source to dest with setting the username
// of dest to username, while also generating random keys for the new
// ShFile and storing it in DS returns the newlymade shFile's uuid, encK, hMACK
func copyStoreShFile(username string, source *ShFile, dest *ShFile) (uuid.UUID, []byte, []byte, error) {
	dest.UName = username
	dest.IsOwner = false
	dest.FragUUID = source.FragUUID
	dest.FragSKey = source.FragSKey
	dest.FragHMACK = source.FragHMACK
	id, encK, hMACK := structInitIDKeys()
	data, err := json.Marshal(dest)
	if err != nil {
		return uuid.UUID{}, nil, nil, err
	}
	err = EncHMACStore(data, encK, hMACK, id)
	if err != nil {
		return uuid.UUID{}, nil, nil, err
	}
	return id, encK, hMACK, nil
}

// API

func InitUser(username string, password string) (userdataptr *User, err error) {
	if len(username) <= 0 {
		return nil, errors.New("username should not be empty")
	}
	if len(password) <= 0 {
		return nil, errors.New("Password should not be empty")
	}
	var userdata User
	userdata.Username = username
	var uid uuid.UUID = getUserID(username)
	// check if the user exists
	_, ok := userlib.DatastoreGet(uid)
	if ok {
		return nil, errors.New("user with username " + username + " already exists")
	}
	// Generate Keys

	privSKey, pubSKey, err := userlib.DSKeyGen()

	if err != nil {
		return nil, err
	}

	pubKey, privKey, err := userlib.PKEKeyGen()
	if err != nil {
		return nil, err
	}

	userdata.PrivateKey = privKey
	userdata.SignKey = privSKey

	// store public keys in keystore

	err = userlib.KeystoreSet(username+"EncryptK", pubKey)
	if err != nil {
		return nil, err
	}

	err = userlib.KeystoreSet(username+"VerifyK", pubSKey)
	if err != nil {
		return nil, err
	}

	// create the root key for further key generation

	rKey := userlib.Argon2Key([]byte(password), []byte(username), 16)

	// write a method for deriving enc key and HMACK from root key
	encKey, hmack, err := deriveKeysFromR(rKey, 16)
	if err != nil {
		return nil, err
	}
	// marhsall to json then store in datastore
	userBytes, err := json.Marshal(userdata)
	if err != nil {
		return nil, err
	}
	//encrypt then mac
	encData, err := EncryptHMAC(encKey, hmack, userBytes)
	if err != nil {
		return nil, err
	}
	// store
	userlib.DatastoreSet(uid, encData)
	return &userdata, nil
}

func GetUser(username string, password string) (userdataptr *User, err error) {
	// check the case with an invalid username
	if len(username) == 0 {
		return nil, errors.New("usernames cannot be empty")
	}
	uid := getUserID(username)
	rKey := userlib.Argon2Key([]byte(password), []byte(username), 16)
	encKey, hMACK, err := deriveKeysFromR(rKey, 16)
	if err != nil {
		return nil, err
	}
	// get the encrypted user struct
	userData, ok := userlib.DatastoreGet(uid)
	if !ok {
		return nil, errors.New("username does not exist")
	}
	// verify and decrypt
	decData, err := VerifyHMACDecrypt(encKey, hMACK, userData)
	// the user struct cannot be obtained due to tampering with the user struct
	var userdata User
	if err != nil {
		return nil, err
	}
	err = json.Unmarshal(decData, &userdata)
	if err != nil {
		return nil, err
	}
	userdataptr = &userdata
	return userdataptr, nil
}

func (userdata *User) StoreFile(filename string, content []byte) (err error) {

	// fetch the file invitation from datastore
	invite, ok, err := fetchInvite(userdata, filename)
	if err != nil {
		return err
	}
	// see if it already exists
	if ok {
		// if yes get fragments
		sharedFile, err := getShFile(invite.SFUUID, invite.SFK, invite.SFHMACK)
		if err != nil {
			return err
		}
		fragments, err := getFragments(sharedFile.FragUUID, sharedFile.FragSKey, sharedFile.FragHMACK)
		if err != nil {
			return err
		}
		// zero out the counter and call appendfile
		fragments.Cnt = 0
		data, err := json.Marshal(fragments)
		if err != nil {
			return err
		}
		err = EncHMACStore(data, sharedFile.FragSKey, sharedFile.FragHMACK, sharedFile.FragUUID)
		if err != nil {
			return err
		}
		err = userdata.AppendToFile(fragments.FName, content)
		if err != nil {
			return err
		}
		return nil
	}

	// if not, initialize the file
	err = initFile(userdata, filename)
	if err != nil {
		return err
	}

	// then call append file to save
	err = userdata.AppendToFile(filename, content)
	if err != nil {
		return err
	}
	return nil
}

func (userdata *User) AppendToFile(filename string, content []byte) error {
	invite, ok, err := fetchInvite(userdata, filename)
	if err != nil {
		return err
	}
	if !ok {
		return errors.New("no invitation found for that filename")
	}
	// else fetch the shared file from the file invite
	shFile, err := getShFile(invite.SFUUID, invite.SFK, invite.SFHMACK)
	if err != nil {
		return err
	}
	// then get the fragments
	fragments, err := getFragments(shFile.FragUUID, shFile.FragSKey, shFile.FragHMACK)
	if err != nil {
		return err
	}
	// then get the id for the next fragment
	nextFragUID, err := genFragID(fragments.OwnerUN, fragments.FName, fragments.Cnt)
	if err != nil {
		return err
	}
	err = EncHMACStore(content, fragments.EKey, fragments.HMACK, nextFragUID)
	if err != nil {
		return err
	}
	// store and increase the counter
	fragments.Cnt += 1
	data, err := json.Marshal(fragments)
	if err != nil {
		return err
	}
	// store the fragments struct with updated counter
	return EncHMACStore(data, fragments.EKey, fragments.HMACK, shFile.FragUUID)
}

func (userdata *User) LoadFile(filename string) (content []byte, err error) {
	// get the invite
	invite, ok, err := fetchInvite(userdata, filename)
	if err != nil {
		return nil, err
	}
	if !ok {
		return nil, errors.New("no invitation to edit the file for this user for this filename")
	}
	// get to fragments through the invite
	shFile, err := getShFile(invite.SFUUID, invite.SFK, invite.SFHMACK)
	if err != nil {
		return nil, err
	}
	fragments, err := getFragments(shFile.FragUUID, shFile.FragSKey, shFile.FragHMACK)
	if err != nil {
		return nil, err
	}
	content, err = glueFragments(fragments, fragments.EKey, fragments.HMACK)
	if err != nil {
		return nil, err
	}
	return content, nil
	// put them together into a byte array
}

// function for storing a file with encryption and HMAC in DS
// just cause I keep rewriting this stuff
func storeFile(file *File, id uuid.UUID, encK []byte, hMACK []byte) error {
	fileBytes, err := json.Marshal(*file)
	if err != nil {
		return err
	}
	return EncHMACStore(fileBytes, encK, hMACK, id)
}

func (userdata *User) CreateInvitation(filename string, recipientUsername string) (
	invitationPtr uuid.UUID, err error) {
	// get user's public keys too check if they exist
	_, _, err = getPubUserKeys(recipientUsername)
	if err != nil {
		return uuid.UUID{}, err
	}
	// check if the user has an invitation for the file
	invite, ok, err := fetchInvite(userdata, filename)
	if err != nil {
		return uuid.UUID{}, err
	}
	if !ok {
		return uuid.Nil, errors.New("user does not have this filename in their filespace")
	}
	// get their shFile instance
	shFile, err := getShFile(invite.SFUUID, invite.SFK, invite.SFHMACK)
	if err != nil {
		return uuid.UUID{}, err
	}
	// if the owner is sharing create a new ShFile instance
	if shFile.IsOwner {
		var newShFile ShFile
		nShID, nShEncK, nShHMACK, err := copyStoreShFile(recipientUsername, shFile, &newShFile)
		if err != nil {
			return uuid.UUID{}, err
		}
		// get the file to append new user to children list
		file, err := getFile(shFile.FileUUID, shFile.FKey, shFile.FHMACK)
		if err != nil {
			return uuid.UUID{}, err
		}
		// here we append sender to filename to distinguish the
		// invite from the one sent to oneself
		inviteID := getInviteUID(recipientUsername, filename+"sender")
		newInvite, err := createStoreInvite(nShID, nShEncK, nShHMACK, userdata,
			recipientUsername, inviteID)
		if err != nil {
			return uuid.UUID{}, err
		}
		// now add the invite to the children list for the file
		// and re-store the file
		file.Children = append(file.Children, *newInvite)
		err = storeFile(file, shFile.FileUUID, shFile.FKey, shFile.FHMACK)
		if err != nil {
			return uuid.Nil, err
		}
		return inviteID, nil
	} else {
		// just create a new invite, store it with shFile stuff of the non-owner
		// same thing with tmp
		inviteID := getInviteUID(recipientUsername, filename+"sender")
		_, err = createStoreInvite(invite.SFUUID, invite.SFK, invite.SFHMACK, userdata, recipientUsername,
			inviteID)
		if err != nil {
			return uuid.UUID{}, err
		}
		return inviteID, nil
	}
}

func (userdata *User) AcceptInvitation(senderUsername string, invitationPtr uuid.UUID, filename string) error {
	// verify signature on the invitation with sender's sign key
	encInviteBytes, ok := userlib.DatastoreGet(invitationPtr)
	if !ok {
		return errors.New("invitation does not exist")
	}
	// decrypt it with user's private key
	_, verifyK, err := getPubUserKeys(senderUsername)
	if err != nil {
		return errors.New("sender of the invitation does not exist in DS")
	}
	inviteBytes, err := verifyDecrypt(encInviteBytes, userdata.PrivateKey, verifyK)
	if err != nil {
		return errors.New("invitation signature could not be verified")
	}
	var invite Invitation
	err = json.Unmarshal(inviteBytes, &invite)
	if err != nil {
		return err
	}
	// copy the invitation to hash(username + filename + F)
	newID := getInviteUID(userdata.Username, filename)
	_, exists := userlib.DatastoreGet(newID)
	if exists {
		return errors.New("file with this name already exists")
	}
	_, err = createStoreInvite(invite.SFUUID, invite.SFK, invite.SFHMACK,
		userdata, userdata.Username, newID)
	if err != nil {
		return err
	}
	// delete the old invitation with old filename from DS
	userlib.DatastoreDelete(invitationPtr)
	return nil
}

func (userdata *User) RevokeAccess(filename string, recipientUsername string) error {
	// get the invite -> shFile -> Fragments and File for this file
	invite, ok, err := fetchInvite(userdata, filename)
	if err != nil {
		return err
	}
	if !ok {
		return errors.New("file does not exist in user's file space")
	}
	shFile, err := getShFile(invite.SFUUID, invite.SFK, invite.SFHMACK)
	if err != nil {
		return err
	}
	if !shFile.IsOwner {
		return errors.New("insufficient rights! only owners can revoke access")
	}
	fragments, err := getFragments(shFile.FragUUID, shFile.FragSKey, shFile.FragHMACK)
	if err != nil {
		return err
	}
	file, err := getFile(shFile.FileUUID, shFile.FKey, shFile.FHMACK)
	if err != nil {
		return err
	}
	// store fragments in a new place
	newFrID, newFrK, newFrHMACK := structInitIDKeys()

	fragBytes, err := json.Marshal(fragments)
	if err != nil {
		return err
	}
	err = EncHMACStore(fragBytes, newFrK, newFrHMACK, newFrID)
	if err != nil {
		return err
	}
	flag := false
	// then update everyone but the recipient's shared file structs to include
	// new fragments and delete recipient's shFile instance
	for _, curInv := range file.Children {
		shF, err := getShFile(curInv.SFUUID, curInv.SFK, curInv.SFHMACK)
		if err != nil {
			return err
		}
		if shF.UName == recipientUsername {
			flag = true
			userlib.DatastoreDelete(curInv.SFUUID)
		} else {
			shF.FragUUID = newFrID
			shF.FragSKey = newFrK
			shF.FragHMACK = newFrHMACK
			shFBytes, err := json.Marshal(shF)
			if err != nil {
				return err
			}
			err = EncHMACStore(shFBytes, curInv.SFK, curInv.SFHMACK, curInv.SFUUID)
			if err != nil {
				return err
			}
		}
	}
	if !flag {
		return errors.New("revoked user had no access to the file")
	}
	return nil
}
