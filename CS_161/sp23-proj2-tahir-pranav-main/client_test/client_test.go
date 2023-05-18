package client_test

// You MUST NOT change these default imports.  ANY additional imports may
// break the autograder and everyone will be sad.

import (
	// Some imports use an underscore to prevent the compiler from complaining
	// about unused imports.

	_ "encoding/hex"
	_ "errors"
	_ "strconv"
	"strings"
	"testing"

	// A "dot" import is used here so that the functions in the ginko and gomega
	// modules can be used without an identifier. For example, Describe() and
	// Expect() instead of ginko.Describe() and gomega.Expect().
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"

	userlib "github.com/cs161-staff/project2-userlib"

	"github.com/cs161-staff/project2-starter-code/client"
)

func TestSetupAndExecution(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Client Tests")
}

// ================================================
// Global Variables (feel free to add more!)
// ================================================
const defaultPassword = "password"
const emptyString = ""
const contentOne = "Bitcoin is Nick's favorite "
const contentTwo = "digital "
const contentThree = "cryptocurrency!"

// ================================================
// Describe(...) blocks help you organize your tests
// into functional categories. They can be nested into
// a tree-like structure.
// ================================================

var _ = Describe("Client Tests", func() {

	// A few user declarations that may be used for testing. Remember to initialize these before you
	// attempt to use them!
	var alice *client.User
	var bob *client.User
	var charles *client.User
	// var doris *client.User
	// var eve *client.User
	// var frank *client.User
	// var grace *client.User
	// var horace *client.User
	// var ira *client.User

	// These declarations may be useful for multi-session testing.
	var alicePhone *client.User
	var aliceLaptop *client.User
	var aliceDesktop *client.User

	var err error

	// A bunch of filenames that may be useful.
	aliceFile := "aliceFile.txt"
	bobFile := "bobFile.txt"
	charlesFile := "charlesFile.txt"
	davidFile := "davidFile.txt"
	// dorisFile := "dorisFile.txt"
	// eveFile := "eveFile.txt"
	// frankFile := "frankFile.txt"
	// graceFile := "graceFile.txt"
	// horaceFile := "horaceFile.txt"
	// iraFile := "iraFile.txt"

	BeforeEach(func() {
		// This runs before each test within this Describe block (including nested tests).
		// Here, we reset the state of Datastore and Keystore so that tests do not interfere with each other.
		// We also initialize
		userlib.DatastoreClear()
		userlib.KeystoreClear()
	})

	Describe("Basic Tests", func() {

		Specify("Basic Test: Testing InitUser/GetUser on a single user.", func() {
			userlib.DebugMsg("Initializing user Alice.")
			alice, err = client.InitUser("alice", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Getting user Alice.")
			aliceLaptop, err = client.GetUser("alice", defaultPassword)
			Expect(err).To(BeNil())
		})

		Specify("Basic Test: Testing Single User Store/Load/Append.", func() {
			userlib.DebugMsg("Initializing user Alice.")
			alice, err = client.InitUser("alice", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Storing file data: %s", contentOne)
			err = alice.StoreFile(aliceFile, []byte(contentOne))
			userlib.DebugMsg(aliceFile)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Appending file data: %s", contentTwo)
			err = alice.AppendToFile(aliceFile, []byte(contentTwo))
			Expect(err).To(BeNil())

			userlib.DebugMsg("Appending file data: %s", contentThree)
			err = alice.AppendToFile(aliceFile, []byte(contentThree))
			Expect(err).To(BeNil())

			userlib.DebugMsg("Loading file...")
			data, err := alice.LoadFile(aliceFile)
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne + contentTwo + contentThree)))
		})

		Specify("Basic Test: Testing Create/Accept Invite Functionality with multiple users and multiple instances.", func() {
			userlib.DebugMsg("Initializing users Alice (aliceDesktop) and Bob.")
			aliceDesktop, err = client.InitUser("alice", defaultPassword)
			Expect(err).To(BeNil())

			bob, err = client.InitUser("bob", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Getting second instance of Alice - aliceLaptop")
			aliceLaptop, err = client.GetUser("alice", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("aliceDesktop storing file %s with content: %s", aliceFile, contentOne)
			err = aliceDesktop.StoreFile(aliceFile, []byte(contentOne))
			Expect(err).To(BeNil())

			userlib.DebugMsg("aliceLaptop creating invite for Bob.")
			invite, err := aliceLaptop.CreateInvitation(aliceFile, "bob")
			Expect(err).To(BeNil())

			userlib.DebugMsg("Bob accepting invite from Alice under filename %s.", bobFile)
			err = bob.AcceptInvitation("alice", invite, bobFile)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Bob appending to file %s, content: %s", bobFile, contentTwo)
			err = bob.AppendToFile(bobFile, []byte(contentTwo))
			Expect(err).To(BeNil())

			userlib.DebugMsg("aliceDesktop appending to file %s, content: %s", aliceFile, contentThree)
			err = aliceDesktop.AppendToFile(aliceFile, []byte(contentThree))
			Expect(err).To(BeNil())

			userlib.DebugMsg("Checking that aliceDesktop sees expected file data.")
			data, err := aliceDesktop.LoadFile(aliceFile)
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne + contentTwo + contentThree)))

			userlib.DebugMsg("Checking that aliceLaptop sees expected file data.")
			data, err = aliceLaptop.LoadFile(aliceFile)
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne + contentTwo + contentThree)))

			userlib.DebugMsg("Checking that Bob sees expected file data.")
			data, err = bob.LoadFile(bobFile)
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne + contentTwo + contentThree)))

			userlib.DebugMsg("Getting third instance of Alice - alicePhone.")
			alicePhone, err = client.GetUser("alice", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Checking that alicePhone sees Alice's changes.")
			data, err = alicePhone.LoadFile(aliceFile)
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne + contentTwo + contentThree)))
		})

		Specify("Basic Test: Testing Revoke Functionality", func() {
			userlib.DebugMsg("Initializing users Alice, Bob, and Charlie.")
			alice, err = client.InitUser("alice", defaultPassword)
			Expect(err).To(BeNil())

			bob, err = client.InitUser("bob", defaultPassword)
			Expect(err).To(BeNil())

			charles, err = client.InitUser("charles", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Alice storing file %s with content: %s", aliceFile, contentOne)
			alice.StoreFile(aliceFile, []byte(contentOne))

			userlib.DebugMsg("Alice creating invite for Bob for file %s, and Bob accepting invite under name %s.", aliceFile, bobFile)

			invite, err := alice.CreateInvitation(aliceFile, "bob")
			Expect(err).To(BeNil())

			err = bob.AcceptInvitation("alice", invite, bobFile)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Checking that Alice can still load the file.")
			data, err := alice.LoadFile(aliceFile)
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne)))

			userlib.DebugMsg("Checking that Bob can load the file.")
			data, err = bob.LoadFile(bobFile)
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne)))

			userlib.DebugMsg("Bob creating invite for Charles for file %s, and Charlie accepting invite under name %s.", bobFile, charlesFile)
			invite, err = bob.CreateInvitation(bobFile, "charles")
			Expect(err).To(BeNil())

			err = charles.AcceptInvitation("bob", invite, charlesFile)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Checking that Charles can load the file.")
			data, err = charles.LoadFile(charlesFile)
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne)))

			userlib.DebugMsg("Alice revoking Bob's access from %s.", aliceFile)
			err = alice.RevokeAccess(aliceFile, "bob")
			Expect(err).To(BeNil())

			userlib.DebugMsg("Checking that Alice can still load the file.")
			data, err = alice.LoadFile(aliceFile)
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne)))

			userlib.DebugMsg("Checking that Bob/Charles lost access to the file.")
			_, err = bob.LoadFile(bobFile)
			Expect(err).ToNot(BeNil())

			_, err = charles.LoadFile(charlesFile)
			Expect(err).ToNot(BeNil())

			userlib.DebugMsg("Checking that the revoked users cannot append to the file.")
			err = bob.AppendToFile(bobFile, []byte(contentTwo))
			Expect(err).ToNot(BeNil())

			err = charles.AppendToFile(charlesFile, []byte(contentTwo))
			Expect(err).ToNot(BeNil())
		})

	})

	Describe("Written Tests", func() {
		Specify("Test #1 - 3.1 specs", func() {
			userlib.DebugMsg("Initializing users Alice, Bob, bob, and bob (duplicate testing).")
			userlib.DebugMsg("Initializing user Alice.")
			alice, err = client.InitUser("alice", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Initializing user bob.")
			bob, err = client.InitUser("bob", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Initializing another LC bob.")
			charles, err = client.InitUser("bob", defaultPassword)
			Expect(err).ToNot(BeNil())

			userlib.DebugMsg("Initializing user Bob.")
			charles, err = client.InitUser("Bob", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Testing if we can access Bob's file as bob.")
			err = bob.StoreFile(bobFile, []byte(""))
			Expect(err).To(BeNil())
			data, err := charles.LoadFile(bobFile)
			Expect(err).ToNot(BeNil())

			userlib.DebugMsg("Having Bob try to store his file again")
			err = bob.StoreFile(bobFile, []byte(""))
			// it is fine to store the file again so here should be no error
			Expect(err).To(BeNil())

			userlib.DebugMsg("Testing an empty username")
			charles, err = client.InitUser("", defaultPassword)
			Expect(err).ToNot(BeNil())

			userlib.DebugMsg("Testing empty pwd")
			charles, err = client.InitUser("charles", "")
			Expect(err).ToNot(BeNil())

			userlib.DebugMsg("Testing big user, big pwd")
			charles, err = client.InitUser(strings.Repeat("charles", 1000), strings.Repeat("bananaslug", 1200))
			Expect(err).To(BeNil())

			userlib.DebugMsg("Trying append/load without storefile/...")
			userlib.DebugMsg("Appending file data: %s", contentTwo)
			err = alice.AppendToFile(aliceFile, []byte(contentTwo))
			Expect(err).ToNot(BeNil())

			userlib.DebugMsg("Loading file which should be empty to check if append accidentally added...")
			data, err = alice.LoadFile(aliceFile)
			Expect(err).ToNot(BeNil())
			Expect(data).To(Equal([]byte("")))

			userlib.DebugMsg("Trying load without making ")
			userlib.DebugMsg("Loading file...")
			data, err = alice.LoadFile(aliceFile)
			Expect(err).ToNot(BeNil())
		})

		Specify("Test 3.2, 3.5 specs", func() {

			userlib.DebugMsg("Initializing users Alice, Bob.")

			alice, err = client.InitUser("Alice", defaultPassword)
			Expect(err).To(BeNil())

			bob, err = client.InitUser("Bob", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Storing data in Alice's file with no name")
			err = alice.StoreFile("", []byte(contentOne))
			Expect(err).To(BeNil())

			userlib.DebugMsg("Testing multiple sessions now, so getting another Bob to check data after")
			var newBob *client.User
			newBob, err = client.GetUser("Bob", defaultPassword)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Storing data in Bob's file with no name")
			err = bob.StoreFile("", []byte(contentThree))
			Expect(err).To(BeNil())

			userlib.DebugMsg("filenames not unique test")
			data, err := alice.LoadFile("")
			Expect(data).To(Equal([]byte(contentOne)))
			Expect(err).To(BeNil())
			data, err = bob.LoadFile("")
			Expect(data).To(Equal([]byte(contentThree)))
			Expect(err).To(BeNil())

			userlib.DebugMsg("Getting local Bob's file data on newBob")
			data, err = newBob.LoadFile("")
			Expect(data).To(Equal([]byte(contentThree)))
			Expect(err).To(BeNil())

			userlib.DebugMsg("Appending to current bob's session using newbob")
			newBob.AppendToFile("", []byte(contentOne))

			userlib.DebugMsg("Checking current bob's version of the file, should be updated")
			data, err = bob.LoadFile("")
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentThree + contentOne)))

			// newBob should accept an invite to a file, and then bob should be able to access
			userlib.DebugMsg("Alice makes a sharedFile and invites Bob")
			// alice stores hi in sharedfile, then invites Bob
			err = alice.StoreFile("sharedFile", []byte("hi"))
			Expect(err).To(BeNil())
			invite, err := alice.CreateInvitation("sharedFile", "Bob")
			Expect(err).To(BeNil())
			// Bob accepts, and expects data to be hi.
			bob.AcceptInvitation("Alice", invite, "sharedFile")
			userlib.DebugMsg("Bob should be able to see the file on newBob now that bob has accepted")
			data, err = newBob.LoadFile("sharedFile")
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte("hi")))
			// INIT USER tests are done I think
			// GET USER wrong pwd, empty username,

			garbageUser, err := client.GetUser("Alice", "garbagegarbage")
			Expect(err).ToNot(BeNil())
			Expect(garbageUser).To(BeNil())
			garbageUser, err = client.GetUser("", "garbagegarbage")
			Expect(err).ToNot(BeNil())
			Expect(garbageUser).To(BeNil())

			garbageData, err := alice.LoadFile("nonExistentFileName")
			Expect(err).ToNot(BeNil())
			Expect(garbageData).To(BeNil())

			// FILE SHARING, APPENDING, OTHER FUNCS, REVOKING, RESTRICTING, ETC.
			// CROSS SESSION REVOKING, APPENDING, SHARING.

			// STOREFILE FOR SECOND TIME
			err = alice.StoreFile("dummyOne", []byte("hi"))
			Expect(err).To(BeNil())
			err = alice.StoreFile("dummyOne", []byte("how are you"))
			Expect(err).To(BeNil())
			data, err = alice.LoadFile("dummyOne")
			Expect(data).To(Equal([]byte("how are you")))

			// append to file
			// three different appends by two invitees
			// then you add another two people.
			// make sure they all see same version
			david, err := client.InitUser("David", defaultPassword)
			invite, err = bob.CreateInvitation("sharedFile", "David")
			david.AcceptInvitation("Bob", invite, "davidsShared")
			data, err = david.LoadFile("davidsShared")
			Expect(data).To(Equal([]byte("hi")))
			bob.AppendToFile("sharedFile", []byte(" how are you?"))
			data, err = david.LoadFile("davidsShared")
			Expect(data).To(Equal([]byte("hi how are you?")))

			// create invite
			// if filename you're creating invitation for doesn't exist
			invite, err = alice.CreateInvitation("garbagefilename", "")
			Expect(err).ToNot(BeNil())
			// if recipient doesn't exist
			invite, err = alice.CreateInvitation("", "")
			Expect(err).ToNot(BeNil())
			charles, err = client.InitUser("Charles", defaultPassword)
			Expect(err).To(BeNil())
			// if owner creates instance, invites Bob, and OVERWRITES Bob's filename of that same name
			invite, err = alice.CreateInvitation("", "Bob")

			Expect(err).To(BeNil())
			err = bob.AcceptInvitation("Alice", invite, "")
			Expect(err).ToNot(BeNil())
			/*data, err = bob.LoadFile("")
			// BOB'S VERSION OF THE FILE SHOULD BE ALICE'S NOW - CONTENT ONE
			Expect(data).To(Equal([]byte(contentOne)))

			// if the owner isn't creating the instance...
			// bob invites charles
			invite, err = bob.CreateInvitation("", "Charles")
			Expect(err).To(BeNil())
			// charles stores some data of the same name for now
			charles.StoreFile("", []byte("dummy data for the empty string file"))
			charles.AcceptInvitation("Bob", invite, "diffNameLol")
			data, err = charles.LoadFile("diffNameLol")
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte(contentOne)))
			data, err = charles.LoadFile("")
			Expect(err).To(BeNil())
			Expect(data).To(Equal([]byte("dummy data for the empty string file")))
			*/
			// using invalid invitation ptr test for invites
			err = alice.AcceptInvitation("Bob", invite, "dummy")
			Expect(err).ToNot(BeNil())

			// TODO
			// test corrupted data
			// file fragments being the same
			// you use createinvitation, get uid,
			// you go to datastore and do ds.set, in place of uid store garbage
			// if you try to get the same invite with the uid, it should err

			// TODO
			// LOAD file after revoked
			// all the diff session tests but for post-revoked tests
			// appends before and then revoke and after.
			// store after revoked
		})

		Specify("Test: data corruption", func() {
			userlib.DebugMsg("Initializing users charles, david, alice and bob.")
			alice, err = client.InitUser("alice", defaultPassword)
			Expect(err).To(BeNil())

			david, err := client.InitUser("david", defaultPassword)
			Expect(err).To(BeNil())

			charles, err = client.InitUser("charles", defaultPassword)
			Expect(err).To(BeNil())

			bob, err = client.InitUser("bob", defaultPassword)
			Expect(err).To(BeNil())

			err = david.StoreFile(davidFile, []byte(contentOne))
			Expect(err).To(BeNil())
			invite, _ := david.CreateInvitation(davidFile, "charles")
			err = charles.AcceptInvitation("david", invite, charlesFile)
			Expect(err).To(BeNil())
			invite, err = charles.CreateInvitation(charlesFile, "alice")
			userlib.DebugMsg("Corrupting data")
			datastoreMap := userlib.DatastoreGetMap()
			for uuid, bytes := range datastoreMap {
				bytes[0] += 1
				userlib.DatastoreSet(uuid, bytes)
			}
			userlib.DebugMsg("Checking that corrupted data cannot be used")
			_, err = client.GetUser("david", defaultPassword)
			Expect(err).ToNot(BeNil())
			_, err = client.GetUser("charles", defaultPassword)
			Expect(err).ToNot(BeNil())
			err = david.AppendToFile(davidFile, []byte("asdas"))
			Expect(err).ToNot(BeNil())
			err = charles.AppendToFile(charlesFile, []byte("asdas"))
			Expect(err).ToNot(BeNil())
			err = alice.AcceptInvitation("charles", invite, aliceFile)
			Expect(err).ToNot(BeNil())

			_, err = david.LoadFile(davidFile)
			Expect(err).ToNot(BeNil())
			_, err = charles.LoadFile(charlesFile)
			Expect(err).ToNot(BeNil())

			err = david.RevokeAccess(davidFile, "charles")
			Expect(err).ToNot(BeNil())
			_, err = david.CreateInvitation(davidFile, "bob")
			Expect(err).ToNot(BeNil())
		})

		Specify("Revoked Access Test: Revoked Access users lose control", func() {
			userlib.DebugMsg("Initializing users alice and bob")
			alice, err = client.InitUser("alice", defaultPassword)
			Expect(err).To(BeNil())

			bob, err = client.InitUser("bob", defaultPassword)
			Expect(err).To(BeNil())

			err = alice.StoreFile(aliceFile, []byte(contentOne))
			Expect(err).To(BeNil())

			invite, err := alice.CreateInvitation(aliceFile, "bob")
			Expect(err).To(BeNil())

			err = bob.AcceptInvitation("alice", invite, bobFile)
			Expect(err).To(BeNil())

			userlib.DebugMsg("Revoking Access of non existent user")
			err = alice.RevokeAccess(aliceFile, "alien")
			Expect(err).ToNot(BeNil())

			userlib.DebugMsg("Alice revoking access from bob")
			err = alice.RevokeAccess(aliceFile, "bob")
			Expect(err).To(BeNil())

			_, err = bob.LoadFile(bobFile)
			Expect(err).ToNot(BeNil())

			invite, err = bob.CreateInvitation(bobFile, "alice")
			Expect(err).ToNot(BeNil())

			_, err = bob.LoadFile(bobFile)
			Expect(err).ToNot(BeNil())

			err = bob.AppendToFile(bobFile, []byte("hello darkness"))
			Expect(err).ToNot(BeNil())

			err = bob.AcceptInvitation("alice", invite, bobFile)
			Expect(err).ToNot(BeNil())

			err = bob.RevokeAccess(bobFile, "alice")
			Expect(err).ToNot(BeNil())

			err = bob.RevokeAccess(bobFile, "alien")
			Expect(err).ToNot(BeNil())
		})

	})
})
